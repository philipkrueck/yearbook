package de.pomc.yearbook.web.questionnaire;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/questionnaire/{id}")
public class QuestionnaireController {

    @GetMapping
    public String showQuestionnaireView(Model model, @PathVariable("id") Long id) {
        model.addAttribute("commentForm", new CommentForm());
        return "/pages/questionnaire/show";
    }

    @GetMapping("/back")
    public String goBackToBookView(@PathVariable("id") Long id) { return "redirect:/book/1"; }

    @PostMapping("/addComment")
    public String addComment(@PathVariable("id") Long id, @ModelAttribute CommentForm commentForm) {
        // ToDo: do something with the comment
        System.out.print("added comment " + commentForm.getComment());
        return "redirect:/questionnaire/{id}";
    }
}
