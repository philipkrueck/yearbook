package de.pomc.yearbook.web.book;

import de.pomc.yearbook.SampleData;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.web.exceptions.ForbiddenException;
import de.pomc.yearbook.web.exceptions.NotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/book")
public class BookController {

    private Book getBook(Long id) {
        return SampleData.getBooks()
                .stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private List<ParticipationViewModel> getParticipationViewModels(Book book) {
        return book.getParticipations()
                .stream()
                .map(ParticipationViewModelConverter::participationViewModel)
                .collect(Collectors.toList());
    }

    private List<BookViewModel> publicBokViewModels() {
        return SampleData.getBooks()
                .stream()
                .map(BookViewModelConverter::bookViewModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public String showBookView(Model model, @PathVariable("id") Long id) {

        Book book = getBook(id);

        if (book == null) {
            throw new NotFoundException();
        }


        List<ParticipationViewModel> participationViewModels = getParticipationViewModels(book);

        model.addAttribute("bookViewModel", BookViewModelConverter.bookViewModel(book));
        model.addAttribute("questions", book.getQuestions());
        model.addAttribute("participationViewModels", participationViewModels);

        return "pages/book/show";
    }

    @GetMapping("/all")
    public String getAllBooks(Model model) {

        List<Book> publishedBooks = SampleData.getBooks();

        model.addAttribute("bookViewModels", publicBokViewModels());

        return "/pages/book/all";
    }

    @PreAuthorize("authenticated")
    @GetMapping("/{id}/editGeneral")
    public String editGeneralInformation(@PathVariable("id") Long id, Model model) {

        Book book = getBook(id);

        // TODO: check if current user can edit

        if (book == null) {
            throw new NotFoundException();
        }

        if (!book.isOwnedByCurrentUser()) {
            throw new ForbiddenException();
        }

        model.addAttribute("bookViewModel", BookViewModelConverter.bookViewModel(book));

        return "pages/book/editGeneral";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/{id}/editGeneral/update")
    public String updateGeneralInformation(@PathVariable("id") Long id, @ModelAttribute("bookViewModel") BookViewModel bookViewModel) {

        Book book = getBook(id);

        if (book == null) {
            throw new NotFoundException();
        }

        if (!book.isOwnedByCurrentUser()) {
            throw new ForbiddenException();
        }

        book.setName(bookViewModel.getTitle());
        book.setDescription(bookViewModel.getDescription());

        return "redirect:/book/{id}";
    }

    @PreAuthorize("authenticated")
    @GetMapping("/{id}/editQuestions")
    public String editQuestions(@PathVariable("id") Long id, @RequestParam(name = "isInCreationProcess", required = true) boolean isInCreationProcess, Model model) {
        Book book = getBook(id);

        if (book == null) {
            throw new NotFoundException();
        }

        if (!book.isOwnedByCurrentUser()) {
            throw new ForbiddenException();
        }

        model.addAttribute("isInCreationProcess", isInCreationProcess);
        model.addAttribute("bookViewModel", BookViewModelConverter.bookViewModel(book));
        model.addAttribute("questions", book.getQuestions());
        model.addAttribute("newQuestionForm", new NewQuestionForm());

        model.addAttribute("editQuestionsBookViewModel", new EditQuestionsViewModel((long) 1, "Graduation 2020", EditQuestionsViewModel.sampleQuestions, ""));

        return "pages/book/editQuestions";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/{id}/editQuestions/new")
    public String addNewQuestion(@PathVariable("id") Long id, @RequestParam(name = "isInCreationProcess", required = true) boolean isInCreationProcess, @ModelAttribute("newQuestionForm") NewQuestionForm newQuestionForm, RedirectAttributes redirectAttributes) {

        Book book = getBook(id);

        if (book == null) {
            throw new NotFoundException();
        }

        if (!book.isOwnedByCurrentUser()) {
            throw new ForbiddenException();
        }

        ArrayList<String> newList = new ArrayList<>(book.getQuestions());

        newList.add(newQuestionForm.getQuestion());

        book.setQuestions(newList);

        redirectAttributes.addAttribute("isInCreationProcess", isInCreationProcess);

        return "redirect:/book/{id}/editQuestions";
    }

    @PreAuthorize("authenticated")
    @GetMapping("/{id}/editParticipants")
    public String editParticipants(@PathVariable("id") Long id, @RequestParam(name = "isInCreationProcess", required = true) boolean isInCreationProcess, Model model) {

        Book book = getBook(id);
        if (book == null) {
            throw new NotFoundException();
        }

        // TODO: check if current user is allowed to edit the participants

        List<ParticipationViewModel> participationViewModels = getParticipationViewModels(book);

        model.addAttribute("isInCreationProcess", isInCreationProcess);
        model.addAttribute("participationViewModels", participationViewModels);
        model.addAttribute("bookViewModel", BookViewModelConverter.bookViewModel(book));
        model.addAttribute("addUserForm", new AddUserForm());

        return "pages/book/editParticipants";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/{id}/editParticipants/new")
    public String addNewParticipant(@PathVariable("id") Long id, @RequestParam(name = "isInCreationProcess", required = true) boolean isInCreationProcess, @ModelAttribute("addUserForm") AddUserForm addUserForm, RedirectAttributes redirectAttributes) {

        Book book = getBook(id);

        if (book == null) {
            throw new NotFoundException();
        }

        // TODO: check if current user is allowed to edit the participants

        User newParticipant = SampleData.users.stream()
                                                .filter(user -> user.getEmail().equals(addUserForm.getEmail()))
                                                .findFirst()
                                                .orElse(null);

        if (newParticipant == null) {
            System.out.println("Could not find email in DB");
        } else {
            List<Participation> newParticipants = new ArrayList<>(book.getParticipations());

            // ToDo: check that participations doesn't include user

            newParticipants.add(new Participation(newParticipant, false));

            book.setParticipations(newParticipants);
        }

        redirectAttributes.addAttribute("isInCreationProcess", isInCreationProcess);

        return "redirect:/book/{id}/editParticipants";
    }

    @PreAuthorize("authenticated")
    @GetMapping("/create")
    public String createNewBook(Model model) {
        model.addAttribute("bookViewModel", new BookViewModel("", "", false));
        return "pages/book/create";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/create")
    public String submitNewBookCreation(@ModelAttribute("bookViewModel") BookViewModel bookViewModel, RedirectAttributes redirectAttributes) {

        // ToDo: check validity of bookViewModel

        Long nextId = SampleData.getBooks().stream()
                                            .map(Book::getId)
                                            .max(Comparator.comparing(Long::intValue))
                                            .orElse((long) -1) + 1;

        Book book = new Book(nextId, bookViewModel.getTitle(), bookViewModel.getDescription(), SampleData.getUsers().get(0));

        List<Book> books = new ArrayList<>(SampleData.getBooks());
        books.add(book);

        SampleData.setBooks(books);

        redirectAttributes.addAttribute("isInCreationProcess", true);
        redirectAttributes.addAttribute("nextId", nextId);

        return "redirect:/book/{nextId}/editQuestions";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/{id}/deleteQuestion/{questionIndex}")
    public String deleteQuestion(@PathVariable("id") Long id, @PathVariable("questionIndex") int questionIndex, @RequestParam(name = "isInCreationProcess", required = true) boolean isInCreationProcess, RedirectAttributes redirectAttributes) {
        Book book = getBook(id);

        if (book == null) {
            throw new NotFoundException();
        }

        if (!book.isOwnedByCurrentUser()) {
            throw new ForbiddenException();
        }

        ArrayList<String> newList = new ArrayList<>(book.getQuestions());

        newList.remove(questionIndex);

        book.setQuestions(newList);

        redirectAttributes.addAttribute("isInCreationProcess", isInCreationProcess);

        return "redirect:/book/{id}/editQuestions";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/{id}/deleteParticipant/{participantId}")
    public String deleteParticipant(@PathVariable("id") Long id, @PathVariable("participantId") int participantId, @RequestParam(name = "isInCreationProcess", required = true) boolean isInCreationProcess, RedirectAttributes redirectAttributes) {
        Book book = getBook(id);

        if (book == null) {
            throw new NotFoundException();
        }

        // TODO: Check if current user is allowed to perform this operation

        List<Participation> newPartcipations = new ArrayList<>(book.getParticipations());

        newPartcipations.remove(participantId);

        book.setParticipations(newPartcipations);

        redirectAttributes.addAttribute("isInCreationProcess", isInCreationProcess);

        return "redirect:/book/{id}/editParticipants";
    }
}
