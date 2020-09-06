package de.pomc.yearbook.web.participation;

import de.pomc.yearbook.SampleData;
import de.pomc.yearbook.participation.Participation;
import de.pomc.yearbook.participation.ParticipationService;
import de.pomc.yearbook.user.User;
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

    private boolean currentUserIsParticipant(Participation participation) {
        User user = userService.findCurrentUser();
        if (user == null) {
            return false;
        }
        return participation.getParticipant().getId().equals(user.getId());
    }

    @ModelAttribute("participation")
    private Participation getParticipation(@PathVariable("id") Long id) {
        Participation participation = participationService.getParticipationWithID(id);
        if (!currentUserIsParticipant(participation)) {
            throw new ForbiddenException();
        }

        return participation;
    }

    @GetMapping
    @PreAuthorize("authenticated")
    public String showEditParticipationView(@PathVariable("id") Long id, Model model) {

        model.addAttribute("editAnswersForm", new EditAnswersForm(getParticipation(id).getOldAnswers()));

        // ToDo: use participation.getBook() in future
        model.addAttribute("book", SampleData.getBooks().get(0));

        return "pages/participation/edit";
    }

    @PostMapping("/update")
    @PreAuthorize("authenticated")
    public String updateQuestions(@PathVariable("id") Long id, @ModelAttribute("editAnswersForm") EditAnswersForm editAnswersForm) {
        // ToDo: add form validation here
        Participation participation = participationService.getParticipationWithID(id);

        participation.setOldAnswers(editAnswersForm.getAnswers());

        participationService.save(participation);

        return "redirect:/participation/{id}";
    }
}
