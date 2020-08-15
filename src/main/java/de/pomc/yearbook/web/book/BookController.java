package de.pomc.yearbook.web.book;

import de.pomc.yearbook.web.exceptions.ForbiddenException;
import de.pomc.yearbook.web.exceptions.NotFoundException;
import de.pomc.yearbook.web.questionnaire.QuestionnaireViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
    public String editQuestions(@PathVariable("id") Long id, Model model) {
        model.addAttribute("editQuestionsBookViewModel", new EditQuestionsViewModel("Graduation 2020", EditQuestionsViewModel.sampleQuestions));

        return "pages/book/editQuestions";
    }

    @GetMapping("/{id}/editParticipants")
    public String editParticipants(@PathVariable("id") Long id) {
        return "pages/book/editParticipants";
    }

    @GetMapping("/create")
    public String createNewBook(Model model) {
        model.addAttribute("createBookViewModel", new CreateBookViewModel("Graduation 2020", ""));
        return "pages/book/create";
    }

    @PostMapping("/create")
    public String submitNewBookCreation(@ModelAttribute CreateBookViewModel createBookViewModel) {

        System.out.println("name: " + createBookViewModel.getName());
        System.out.println("description: " + createBookViewModel.getDescription());
        return "redirect:/book/1/editQuestions";
    }
}
