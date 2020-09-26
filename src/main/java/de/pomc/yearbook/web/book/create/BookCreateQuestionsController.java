package de.pomc.yearbook.web.book.create;

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
import java.util.List;

@Controller
@RequestMapping("book/{id}/create/questions")
@RequiredArgsConstructor
public class BookCreateQuestionsController {

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

    @ModelAttribute("questions")
    public List<Question> getQuestions(@PathVariable("id") Long id) {
        return getBook(id).getQuestions();
    }

    @PreAuthorize("authenticated")
    @GetMapping
    public String showCreateQuestionsView(@PathVariable("id") Long id, Model model) {
        // Ideally we would save whether the creation process is completed, and throw a
        // Forbidden Exception if it already is

        model.addAttribute("questions", getBook(id).getQuestions());
        model.addAttribute("newQuestionForm", new NewQuestionForm());
        return "pages/book/createQuestions";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/new")
    public String addNewQuestion(@PathVariable("id") Long id, @ModelAttribute("newQuestionForm") @Valid NewQuestionForm newQuestionForm, BindingResult bindingResult) {
        // Ideally we would save whether the creation process is completed, and throw a
        // Forbidden Exception if it already is

        if(bindingResult.hasErrors()){
            return "pages/book/createQuestions";
        }

        Book book = getBook(id);
        bookService.addQuestion(book, new Question(newQuestionForm.getQuestion()));

        return "redirect:/book/{id}/create/questions";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/delete/{questionIndex}")
    public String deleteQuestion(@PathVariable("id") Long id, @PathVariable("questionIndex") int questionIndex) {
        // Ideally we would save whether the creation process is completed, and throw a
        // Forbidden Exception if it already is

        Book book = getBook(id);

        if (book.getQuestions().size() < questionIndex || questionIndex < 0) {
            throw new ForbiddenException();
        }

        book.getQuestions().remove(questionIndex);
        bookService.save(book);

        return "redirect:/book/{id}/create/questions";
    }

}
