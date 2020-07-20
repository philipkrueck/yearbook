package de.pomc.yearbook.web.questionnaire;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/questionnaire/{id}/edit")
public class QuestionnaireEditController {

    @GetMapping
    public String showEditQuestionnaireView(Model model, @PathVariable("id") Long id) {
        model.addAttribute("questionnaireViewModel", new QuestionnaireViewModel());
        return "pages/questionnaire/edit";
    }

    @GetMapping("/back")
    public String goBackToQuestionnaireView(@PathVariable("id") Long id) { return "redirect:/questionnaire/{id}"; }

    @PostMapping("/update")
    public String updateQuestionnaire(@PathVariable("id") Long id, @ModelAttribute QuestionnaireViewModel questionnaireViewModel) {
        System.out.println("new question 1: " + questionnaireViewModel.getQuestionOne());
        System.out.println("new question 2: " + questionnaireViewModel.getQuestionTwo());
        System.out.println("new question 3: " + questionnaireViewModel.getQuestionThree());
        return "redirect:/questionnaire/{id}";
    }
}
