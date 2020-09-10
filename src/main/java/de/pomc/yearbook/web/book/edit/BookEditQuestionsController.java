package de.pomc.yearbook.web.book.edit;

import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.book.Question;
import de.pomc.yearbook.web.book.NewQuestionForm;
import de.pomc.yearbook.web.exceptions.ForbiddenException;
import de.pomc.yearbook.web.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/book/{id}/edit/questions")
@RequiredArgsConstructor
public class BookEditQuestionsController {

    private final BookService bookService;

    @ModelAttribute("book")
    private Book getBook(@PathVariable("id") Long id) {
        Book book = bookService.getBookWithID(id);

        if (book == null) {
            throw new NotFoundException();
        }

        if (!book.currentUserIsOwner()) {
            throw new ForbiddenException();
        }

        return book;
    }

    @PreAuthorize("authenticated")
    @GetMapping
    public String showEditQuestionsView(@PathVariable("id") Long id, Model model) {
        model.addAttribute("questions", getBook(id).getQuestions());
        model.addAttribute("newQuestionForm", new NewQuestionForm());

        return "pages/book/editQuestions";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/new")
    public String addNewQuestion(@PathVariable("id") Long id, @ModelAttribute("newQuestionForm") @Valid NewQuestionForm newQuestionForm, BindingResult bindingResult) {
        // ToDo: make sure that there are no participations which are filled in

        if(bindingResult.hasErrors()){
            return "pages/book/editQuestions";
        }

        Book book = getBook(id);
        bookService.addQuestion(book, new Question(newQuestionForm.getQuestion()));

        return "redirect:/book/{id}/edit/questions";
    }


    @PreAuthorize("authenticated")
    @PostMapping("/delete/{questionIndex}")
    public String deleteQuestion(@PathVariable("id") Long id, @PathVariable("questionIndex") int questionIndex) {
        Book book = getBook(id);

        if (book.questionCanNotBeDeletedAt(questionIndex)) {
            throw new ForbiddenException();
        }

        if (book.getQuestions().size() < questionIndex || questionIndex < 0) {
            throw new ForbiddenException();
        }

        book.getQuestions().remove(questionIndex);
        bookService.save(book);

        return "redirect:/book/{id}/edit/questions";
    }
}
