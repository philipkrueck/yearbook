package de.pomc.yearbook.web.book;

import de.pomc.yearbook.SampleData;
import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.web.exceptions.ForbiddenException;
import de.pomc.yearbook.web.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

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
    @GetMapping()
    public String showEditQuestionsView(@PathVariable("id") Long id, @RequestParam(name = "isInCreationProcess", required = true) boolean isInCreationProcess, Model model) {
        model.addAttribute("isInCreationProcess", isInCreationProcess);
        model.addAttribute("questions", getBook(id).getQuestions());
        model.addAttribute("newQuestionForm", new NewQuestionForm());

        return "pages/book/editQuestions";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/new")
    public String addNewQuestion(@PathVariable("id") Long id, @RequestParam(name = "isInCreationProcess", required = true) boolean isInCreationProcess, @ModelAttribute("newQuestionForm") NewQuestionForm newQuestionForm, RedirectAttributes redirectAttributes) {
        Book book = getBook(id);

        ArrayList<String> newList = new ArrayList<>(book.getQuestions());
        newList.add(newQuestionForm.getQuestion());
        book.setQuestions(newList);

        redirectAttributes.addAttribute("isInCreationProcess", isInCreationProcess);
        return "redirect:/book/{id}/edit/questions";
    }


    @PreAuthorize("authenticated")
    @PostMapping("/delete/{questionIndex}")
    public String deleteQuestion(@PathVariable("id") Long id, @PathVariable("questionIndex") int questionIndex, @RequestParam(name = "isInCreationProcess", required = true) boolean isInCreationProcess, RedirectAttributes redirectAttributes) {
        Book book = getBook(id);

        ArrayList<String> newList = new ArrayList<>(book.getQuestions());
        newList.remove(questionIndex);
        book.setQuestions(newList);

        redirectAttributes.addAttribute("isInCreationProcess", isInCreationProcess);
        return "redirect:/book/{id}/edit/questions";
    }
}
