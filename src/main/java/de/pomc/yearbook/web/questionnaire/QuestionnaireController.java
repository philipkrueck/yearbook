package de.pomc.yearbook.web.questionnaire;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questionnaire/{id}")
public class QuestionnaireController {

    @GetMapping
    public String show(@PathVariable("id") Long id) {
        return "pages/questionnaire/questionnaire";
    }
}
