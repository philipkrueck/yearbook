package de.pomc.yearbook.web.book;

import de.pomc.yearbook.SampleData;
import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.book.Participation;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.web.exceptions.NotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/book/{id}/edit/participants")
public class BookEditParticipantsController {

    private Book getBook(Long id) {
        return SampleData.getBooks()
                .stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private List<Participation> getParticipations(Book book) {
        return book.getParticipations();
    }

    @PreAuthorize("authenticated")
    @PostMapping("/{id}/deleteParticipant/{participantId}")
    public String deleteParticipant(@PathVariable("id") Long id, @PathVariable("participantId") int participantId, @RequestParam(name = "isInCreationProcess", required = true) boolean isInCreationProcess, RedirectAttributes redirectAttributes) {
        Book book = getBook(id);

        if (book == null) {
            throw new NotFoundException();
        }

        // TODO: Check if current user is allowed to perform this operation

        List<Participation> newPartcipations = new ArrayList<>(book.getParticipations());

        newPartcipations.remove(participantId);

        book.setParticipations(newPartcipations);

        redirectAttributes.addAttribute("isInCreationProcess", isInCreationProcess);

        return "redirect:/book/{id}/editParticipants";
    }

    @PreAuthorize("authenticated")
    @GetMapping("/{id}/editParticipants")
    public String editParticipants(@PathVariable("id") Long id, @RequestParam(name = "isInCreationProcess", required = true) boolean isInCreationProcess, Model model) {

        Book book = getBook(id);
        if (book == null) {
            throw new NotFoundException();
        }

        // TODO: check if current user is allowed to edit the participants

        List<Participation> participations = getParticipations(book);

        model.addAttribute("isInCreationProcess", isInCreationProcess);
        model.addAttribute("participationViewModels", participations);
        model.addAttribute("bookViewModel", BookViewModelConverter.bookViewModel(book));
        model.addAttribute("addUserForm", new AddUserForm());

        return "pages/book/editParticipants";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/{id}/editParticipants/new")
    public String addNewParticipant(@PathVariable("id") Long id, @RequestParam(name = "isInCreationProcess", required = true) boolean isInCreationProcess, @ModelAttribute("addUserForm") AddUserForm addUserForm, RedirectAttributes redirectAttributes) {

        Book book = getBook(id);

        if (book == null) {
            throw new NotFoundException();
        }

        // TODO: check if current user is allowed to edit the participants

        User newParticipant = SampleData.users.stream()
                .filter(user -> user.getEmail().equals(addUserForm.getEmail()))
                .findFirst()
                .orElse(null);

        if (newParticipant == null) {
            System.out.println("Could not find email in DB");
        } else {
            List<Participation> newParticipants = new ArrayList<>(book.getParticipations());

            // ToDo: check that participations doesn't include user

            Long nextID = newParticipants.get(newParticipants.size()).getId() + 1;

            newParticipants.add(new Participation(nextID, newParticipant, false));

            book.setParticipations(newParticipants);
        }

        redirectAttributes.addAttribute("isInCreationProcess", isInCreationProcess);

        return "redirect:/book/{id}/editParticipants";
    }
}
