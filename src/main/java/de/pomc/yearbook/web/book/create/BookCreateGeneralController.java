package de.pomc.yearbook.web.book.create;

import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.user.UserService;
import de.pomc.yearbook.web.book.BookFormConverter;
import de.pomc.yearbook.web.book.CreateBookForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("book/create")
@RequiredArgsConstructor
public class BookCreateGeneralController {

    private final UserService userService;
    private final BookService bookService;

    @PreAuthorize("authenticated")
    @GetMapping
    public String showCreateNewBookView(Model model) {
        model.addAttribute("createBookForm", new CreateBookForm());
        return "pages/book/createGeneral";
    }

    @PreAuthorize("authenticated")
    @PostMapping
    public String submitNewBookCreation(@ModelAttribute("createBookForm") @Valid CreateBookForm createBookForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            return "pages/book/createGeneral";
        }

        Book book = BookFormConverter.book(createBookForm, userService.findCurrentUser()); // user must be there!
        bookService.save(book);
        redirectAttributes.addAttribute("bookId", book.getId());
        return "redirect:/book/{bookId}/create/questions";

    }
}
