package de.pomc.yearbook.web.book;

import de.pomc.yearbook.SampleData;
import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.participation.Participation;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserService;
import de.pomc.yearbook.web.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/book/{id}/edit/participants")
@RequiredArgsConstructor
public class BookEditParticipantsController {

    private final BookService bookService;
    private final UserService userService;

    private Book getBook(Long id) {
        Book book = bookService.getBookWithID(id);
        if (book == null) {
            throw new NotFoundException();
        }
        return book;
    }

    @PreAuthorize("authenticated")
    @PostMapping("/delete/{participantId}")
    public String deleteParticipant(@PathVariable("id") Long id, @PathVariable("participantId") int participantId, @RequestParam(name = "isInCreationProcess", required = true) boolean isInCreationProcess, RedirectAttributes redirectAttributes) {
        Book book = getBook(id);

        // TODO: Check if current user is allowed to perform this operation

        // List<Participation> newPartcipations = new ArrayList<>(book.getParticipations());
        // newPartcipations.remove(participantId);
        // book.setParticipations(newPartcipations);

        redirectAttributes.addAttribute("isInCreationProcess", isInCreationProcess);
        return "redirect:/book/{id}/edit/participants";
    }

    @PreAuthorize("authenticated")
    @GetMapping
    public String editParticipants(@PathVariable("id") Long id, @RequestParam(name = "isInCreationProcess", required = true) boolean isInCreationProcess, Model model, RedirectAttributes redirectAttributes) {
        Book book = getBook(id);

        // TODO: check if current user is allowed to edit the participants


        model.addAttribute("isInCreationProcess", isInCreationProcess);
        // model.addAttribute("participations", book.getParticipations());
        model.addAttribute("book", book);
        model.addAttribute("addUserForm", new AddUserForm());

        redirectAttributes.addAttribute("isInCreationProcess", isInCreationProcess);
        return "pages/book/editParticipants";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/new")
    public String addNewParticipant(@PathVariable("id") Long id, @RequestParam(name = "isInCreationProcess", required = true) boolean isInCreationProcess, @ModelAttribute("addUserForm") @Valid AddUserForm addUserForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        //if(bindingResult.hasErrors()){
            //return "/pages/book/{id}/edit/participants";
        //}

        Book book = getBook(id);

        // TODO: check if current user is allowed to edit the participants

        User newParticipant = userService.findUserByEmail(addUserForm.getEmail());

        if (newParticipant == null) {
            System.out.println("Could not find email in DB");
        } else {
            // List<Participation> newParticipants = new ArrayList<>(book.getParticipations());

            // ToDo: check that participations doesn't include user

            // Note: This id will be generated once we have the database up and running
            // Long nextID = newParticipants.isEmpty() ? 0 : newParticipants.get(newParticipants.size()-1).getId() + 1;
            // newParticipants.add(new Participation(nextID, newParticipant, false, SampleData.getDefaultAnswers(), SampleData.getComments()));

            // book.setParticipations(newParticipants);
        }

        redirectAttributes.addAttribute("isInCreationProcess", isInCreationProcess);
        return "redirect:/book/{id}/edit/participants";
    }
}
