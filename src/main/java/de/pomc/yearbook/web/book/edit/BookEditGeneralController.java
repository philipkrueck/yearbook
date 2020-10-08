package de.pomc.yearbook.web.book.edit;

import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.web.book.BookForm;
import de.pomc.yearbook.web.book.BookFormConverter;
import de.pomc.yearbook.web.book.BookImageFormConverter;
import de.pomc.yearbook.web.exceptions.ForbiddenException;
import de.pomc.yearbook.web.exceptions.NotFoundException;
import de.pomc.yearbook.web.profile.UserImageFormConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

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
        model.addAttribute("bookImageForm", BookImageFormConverter.bookImageForm(getBook(id)));
        return "pages/book/editGeneral";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/update")
    public String updateGeneralInformation(@PathVariable("id") Long id, @ModelAttribute("bookForm") @Valid BookForm bookForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "pages/book/editGeneral";
        }

        Book book = getBook(id);
        BookFormConverter.update(book, bookForm);

        bookService.save(book);

        return "redirect:/book/{id}";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/addBookImage")
    public String addBookImage(@PathVariable("id") Long id, final @RequestParam("image") MultipartFile image) {
        try {
            Book book = getBook(id);
            book.setImage(image.getBytes());
            bookService.save(book);
            return "redirect:/";
        }
        catch (IOException e) {
            throw new ForbiddenException();
        }
    }
}
