package de.pomc.yearbook.web.book;

import de.pomc.yearbook.SampleData;
import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.book.Participation;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.web.exceptions.ForbiddenException;
import de.pomc.yearbook.web.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public String showBookView(Model model, @PathVariable("id") Long id) {

        Book book = bookService.getBookWithID(id);

        if (book == null) {
            throw new NotFoundException();
        }

        List<Participation> participations = book.getParticipations();

        model.addAttribute("book", book);
        model.addAttribute("questions", book.getQuestions());
        model.addAttribute("participations", participations);

        return "pages/book/show";
    }

    @GetMapping("/all")
    public String showAllBooksView(Model model) {

        model.addAttribute("publishedBooks", bookService.getPublishedBooks());

        return "/pages/book/all";
    }
}
