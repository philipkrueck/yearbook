package de.pomc.yearbook.web.book;

import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.user.UserService;
import de.pomc.yearbook.web.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final UserService userService;

    @GetMapping("/{id}")
    public String showBookView(Model model, @PathVariable("id") Long id) {
        Book book = bookService.getBookWithID(id);

        if (book == null) {
            throw new NotFoundException();
        }

        // TODO: check if current user is allowed to view this book -> public book || user is participant


        model.addAttribute("book", book);
        model.addAttribute("questions", book.getQuestions());
        model.addAttribute("participations", book.getParticipations());

        return "pages/book/show";
    }

    @GetMapping("/all")
    public String showAllBooksView(Model model) {
        model.addAttribute("publishedBooks", bookService.getPublishedBooks());

        return "/pages/book/all";
    }
}
