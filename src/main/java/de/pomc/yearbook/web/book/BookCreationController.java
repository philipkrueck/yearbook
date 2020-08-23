package de.pomc.yearbook.web.book;

import de.pomc.yearbook.SampleData;
import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("book/create")
@RequiredArgsConstructor
public class BookCreationController {

    private final UserService userService;
    private final BookService bookService;

    @PreAuthorize("authenticated")
    @GetMapping()
    public String showCreateNewBookView(Model model) {
        model.addAttribute("newBookForm", new NewBookForm("", ""));
        return "pages/book/create";
    }

    @PreAuthorize("authenticated")
    @PostMapping()
    public String submitNewBookCreation(@ModelAttribute("newBookForm") NewBookForm newBookForm, RedirectAttributes redirectAttributes) {

        // ToDo: check validity of bookViewModel

        // NOTE: once we have the DB, the id will be generated
        Long nextId = SampleData.getBooks().stream()
                .map(Book::getId)
                .max(Comparator.comparing(Long::intValue))
                .orElse((long) -1) + 1;

        Book book = new Book(nextId, newBookForm.getTitle(), newBookForm.getDescription(), userService.findCurrentUser(), false);

        bookService.save(book);

        redirectAttributes.addAttribute("isInCreationProcess", true);
        redirectAttributes.addAttribute("nextId", nextId);

        return "redirect:/book/{nextId}/edit/questions";
    }
}
