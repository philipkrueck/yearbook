package de.pomc.yearbook.web.book;

import de.pomc.yearbook.SampleData;
import de.pomc.yearbook.book.Book;
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
@RequestMapping("book/{id}/create")
public class BookCreationController {

    @PreAuthorize("authenticated")
    @GetMapping("/create")
    public String createNewBook(Model model) {
        model.addAttribute("bookViewModel", new BookViewModel("", "", false));
        return "pages/book/create";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/create")
    public String submitNewBookCreation(@ModelAttribute("bookViewModel") BookViewModel bookViewModel, RedirectAttributes redirectAttributes) {

        // ToDo: check validity of bookViewModel

        Long nextId = SampleData.getBooks().stream()
                .map(Book::getId)
                .max(Comparator.comparing(Long::intValue))
                .orElse((long) -1) + 1;

        Book book = new Book(nextId, bookViewModel.getTitle(), bookViewModel.getDescription(), SampleData.getUsers().get(0), false);

        List<Book> books = new ArrayList<>(SampleData.getBooks());
        books.add(book);

        SampleData.setBooks(books);

        redirectAttributes.addAttribute("isInCreationProcess", true);
        redirectAttributes.addAttribute("nextId", nextId);

        return "redirect:/book/{nextId}/editQuestions";
    }
}
