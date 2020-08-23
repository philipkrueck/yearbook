package de.pomc.yearbook.web.book;

import de.pomc.yearbook.SampleData;
import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.web.exceptions.ForbiddenException;
import de.pomc.yearbook.web.exceptions.NotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/book/{id}/edit/questions")
public class BookEditQuestionsController {

    private Book getBook(Long id) {
        return SampleData.getBooks()
                .stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PreAuthorize("authenticated")
    @GetMapping()
    public String showEditQuestionsView(@PathVariable("id") Long id, @RequestParam(name = "isInCreationProcess", required = true) boolean isInCreationProcess, Model model) {
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
    @PostMapping("/new")
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
    @PostMapping("/delete/{questionIndex}")
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
}
