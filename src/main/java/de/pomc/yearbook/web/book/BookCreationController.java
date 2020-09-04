package de.pomc.yearbook.web.book;

import de.pomc.yearbook.SampleData;
import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Comparator;

@Controller
@RequestMapping("book/create")
@RequiredArgsConstructor
public class BookCreationController {

    private final UserService userService;
    private final BookService bookService;

    @PreAuthorize("authenticated")
    @GetMapping()
    public String showCreateNewBookView(Model model) {
        model.addAttribute("bookForm", new BookForm());
        return "pages/book/create";
    }

    @PreAuthorize("authenticated")
    @PostMapping()
    public String submitNewBookCreation(@ModelAttribute("bookForm") @Valid BookForm bookForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            return "/pages/book/create";
        }

        // NOTE: once we have the DB, the id will be generated
        Long nextId = SampleData.getBooks().stream()
                .map(Book::getId)
                .max(Comparator.comparing(Long::intValue))
                .orElse((long) -1) + 1;

        Book book = new Book(nextId, bookForm.getName(), bookForm.getDescription(), userService.findCurrentUser(), false);

        bookService.save(book);

        redirectAttributes.addAttribute("isInCreationProcess", true);
        redirectAttributes.addAttribute("nextId", book.getId());
        return "redirect:/book/{nextId}/edit/questions";
    }
}
