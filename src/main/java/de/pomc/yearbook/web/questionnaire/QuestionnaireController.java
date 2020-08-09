package de.pomc.yearbook.web.questionnaire;

import de.pomc.yearbook.web.exceptions.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/questionnaire/{id}")
public class QuestionnaireController {

    @GetMapping
    public String showQuestionnaireView(Model model, @PathVariable("id") Long id) {
        if (id == 1234) { // TODO: actually look up the questionnaire in the db
            throw new NotFoundException();
        }
        model.addAttribute("commentForm", new CommentViewModel());
        return "/pages/questionnaire/show";
    }

    @GetMapping("/back")
    public String goBackToBookView(@PathVariable("id") Long id) {
        if (id == 1234) { // TODO: actually look up the questionnaire in the db
            throw new NotFoundException();
        }

        return "redirect:/book/1";
    }

    @PostMapping("/addComment")
    public String addComment(@PathVariable("id") Long id, @ModelAttribute CommentViewModel commentForm) {
        if (id == 1234) { // TODO: actually look up the questionnaire in the db
            throw new NotFoundException();
        }

        // ToDo: do something with the comment
        System.out.print("added comment " + commentForm.getComment());
        return "redirect:/questionnaire/{id}";
    }
}
