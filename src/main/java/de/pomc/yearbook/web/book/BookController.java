package de.pomc.yearbook.web.book;

import de.pomc.yearbook.web.exceptions.ForbiddenException;
import de.pomc.yearbook.web.exceptions.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/book")
public class BookController {

    @GetMapping("/{id}")
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

    @GetMapping("/{id}/editQuestions")
    public String editQuestions(@PathVariable("id") Long id) {
        return "pages/book/editQuestions";
    }

    @GetMapping("/{id}/editParticipants")
    public String editParticipants(@PathVariable("id") Long id) {
        return "pages/book/editParticipants";
    }

    @GetMapping("/create")
    public String createNewBook() {

        return "pages/book/create";
    }
}
