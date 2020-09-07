package de.pomc.yearbook.web.participation;

import de.pomc.yearbook.participation.Participation;
import de.pomc.yearbook.participation.ParticipationService;
import de.pomc.yearbook.user.UserService;
import de.pomc.yearbook.web.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("participation/{id}/edit")
public class ParticipationEditController {

    private final ParticipationService participationService;
    private final UserService userService;

    @ModelAttribute("participation")
    private Participation getParticipation(@PathVariable("id") Long id) {
        Participation participation = participationService.getParticipationWithID(id);
        if (!participation.currentUserIsParticipant()) {
            throw new ForbiddenException();
        }

        return participation;
    }

    @GetMapping
    @PreAuthorize("authenticated")
    public String showEditParticipationView(@PathVariable("id") Long id, Model model) {
        Participation participation = getParticipation(id);

        model.addAttribute("editAnswersForm", new EditAnswersForm(participation.getAnswers()));
        model.addAttribute("book", participation.getBook());

        return "pages/participation/edit";
    }

    @PostMapping("/update")
    @PreAuthorize("authenticated")
    public String updateQuestions(@PathVariable("id") Long id, @ModelAttribute("editAnswersForm") EditAnswersForm editAnswersForm) {
        // ToDo: add form validation here

        Participation participation = participationService.getParticipationWithID(id);

        for (int i = 0; i < participation.getAnswers().size(); i++) {
            participationService.updateAnswer(participation, editAnswersForm.getAnswers().get(i), i);
        }

        return "redirect:/participation/{id}";
    }
}
