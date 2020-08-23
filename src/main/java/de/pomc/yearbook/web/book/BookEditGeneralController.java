package de.pomc.yearbook.web.book;

import de.pomc.yearbook.SampleData;
import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.book.Participation;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.web.exceptions.ForbiddenException;
import de.pomc.yearbook.web.exceptions.NotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/book/{id}/edit/general")
public class BookEditGeneralController {

    private Book getBook(Long id) {
        return SampleData.getBooks()
                .stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PreAuthorize("authenticated")
    @GetMapping
    public String showEditGeneralInformation(@PathVariable("id") Long id, Model model) {

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
    @PostMapping("/update")
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
}
