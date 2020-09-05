package de.pomc.yearbook.web.book;

import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.web.exceptions.ForbiddenException;
import de.pomc.yearbook.web.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book/{id}/edit/general")
@RequiredArgsConstructor
public class BookEditGeneralController {

    private final BookService bookService;

    @ModelAttribute("book")
    public Book getBook(@PathVariable("id") Long id) {
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
    public String showEditGeneralInformation(@PathVariable("id") Long id, Model model) {
        model.addAttribute("bookForm", BookFormConverter.bookForm(getBook(id)));
        return "pages/book/editGeneral";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/update")
    public String updateGeneralInformation(@PathVariable("id") Long id, @ModelAttribute("bookForm") BookForm bookForm) {
        // ToDo: validate BookForm

        Book book = getBook(id);
        BookFormConverter.update(book, bookForm);

        return "redirect:/book/{id}";
    }
}
