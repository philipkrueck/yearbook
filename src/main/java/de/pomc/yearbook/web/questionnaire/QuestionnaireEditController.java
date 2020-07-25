package de.pomc.yearbook.web.questionnaire;

import de.pomc.yearbook.web.exceptions.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/questionnaire/{id}/edit")
public class QuestionnaireEditController {

    @GetMapping
    public String showEditQuestionnaireView(Model model, @PathVariable("id") Long id) {
        if (id == 1234) { // TODO: actually look up the questionnaire in the db
            throw new NotFoundException();
        }

        model.addAttribute("questionnaireViewModel", new QuestionnaireViewModel());
        return "pages/questionnaire/edit";
    }

    @GetMapping("/back")
    public String goBackToQuestionnaireView(@PathVariable("id") Long id) {
        if (id == 1234) { // TODO: actually look up the questionnaire in the db
            throw new NotFoundException();
        }
        return "redirect:/questionnaire/{id}";
    }

    @PostMapping("/update")
    public String updateQuestionnaire(@PathVariable("id") Long id, @ModelAttribute QuestionnaireViewModel questionnaireViewModel) {
        if (id == 1234) { // TODO: actually look up the questionnaire in the db
            throw new NotFoundException();
        }

        System.out.println("new question 1: " + questionnaireViewModel.getQuestionOne());
        System.out.println("new question 2: " + questionnaireViewModel.getQuestionTwo());
        System.out.println("new question 3: " + questionnaireViewModel.getQuestionThree());
        return "redirect:/questionnaire/{id}";
    }
}
