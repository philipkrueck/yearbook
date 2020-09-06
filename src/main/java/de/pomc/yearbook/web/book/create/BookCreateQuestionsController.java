package de.pomc.yearbook.web.book.create;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("book/{id}/create/questions")
public class BookCreateQuestionsController {

    @PreAuthorize("authenticated")
    @GetMapping
    public String showCreateQuestionsView(Model model) {
        return "pages/book/createQuestions";
    }

}
