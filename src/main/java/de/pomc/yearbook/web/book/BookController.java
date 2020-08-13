package de.pomc.yearbook.web.book;

import de.pomc.yearbook.web.exceptions.ForbiddenException;
import de.pomc.yearbook.web.exceptions.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.print.Book;
import java.util.List;

@Controller
@RequestMapping("/book/{id}")
public class BookController {

    @GetMapping
    public String showBookView(Model model, @PathVariable("id") Long id) {
        if (id == 69) {
            throw new ForbiddenException();
        }

        BookViewModel bookViewModel = BookViewModel.getSampleData().get(id);

        if (bookViewModel == null) {
            throw new NotFoundException();
        }

        model.addAttribute("bookViewModel", bookViewModel);

        return "pages/book/book";
    }

    @GetMapping("/edit")
    public String showEditBookView(@PathVariable("id") Long id) {
        if (id == 1234) { // TODO: actually look up the book in the db
            throw new NotFoundException();
        }
        return "pages/book/edit";
    }
}
