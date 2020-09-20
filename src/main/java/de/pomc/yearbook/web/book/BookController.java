package de.pomc.yearbook.web.book;

import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.user.UserService;
import de.pomc.yearbook.web.exceptions.ForbiddenException;
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

    private Book getBook(Long id) {
        Book book = bookService.getBookWithID(id);

        if (book == null) {
            throw new NotFoundException();
        }

        if (!book.isPublished() && !book.currentUserHasParticipation() && !book.currentUserIsOwner()) {
            throw new ForbiddenException();
        }

        return book;
    }

    @GetMapping("/{id}")
    public String showBookView(Model model, @PathVariable("id") Long id) {
        Book book = getBook(id);

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

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") Long id) {
        Book book = getBook(id);

        if (!book.currentUserIsOwner()) {
            throw new ForbiddenException();
        }

        bookService.delete(book);

        return "redirect:/profile";
    }
}
