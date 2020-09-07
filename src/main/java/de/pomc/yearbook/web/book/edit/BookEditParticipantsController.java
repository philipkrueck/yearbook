package de.pomc.yearbook.web.book.edit;

import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.participation.Participation;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserService;
import de.pomc.yearbook.web.book.AddUserForm;
import de.pomc.yearbook.web.book.PromotionForm;
import de.pomc.yearbook.web.exceptions.ForbiddenException;
import de.pomc.yearbook.web.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/book/{id}/edit/participants")
@RequiredArgsConstructor
public class BookEditParticipantsController {

    private final BookService bookService;
    private final UserService userService;

    @ModelAttribute("book")
    public Book getBook(@PathVariable("id") Long id) {
        Book book = bookService.getBookWithID(id);
        if (book == null) {
            throw new NotFoundException();
        }

        if (!book.currentUserIsParticipant() && !book.currentUserIsOwner()) {
            throw new ForbiddenException();
        }

        return book;
    }

    @PreAuthorize("authenticated")
    @GetMapping
    public String showEditParticipantsView(@PathVariable("id") Long id, Model model) {
        model.addAttribute("addUserForm", new AddUserForm());
        model.addAttribute("promotionForm", new PromotionForm(getBook(id).getParticipations()));
        return "pages/book/editParticipants";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/delete/{participantId}")
    public String deleteParticipant(@PathVariable("id") Long id, @PathVariable("participantId") int participantId) {
        Book book = getBook(id);

        if (!book.currentUserCanDelete(participantId)) {
            throw new ForbiddenException();
        }

        book.getParticipations().remove(participantId);
        bookService.save(book);

        return "redirect:/book/{id}/edit/participants";
    }

    @PreAuthorize("authenticated")
    @PostMapping("/new")
    public String addNewParticipant(@PathVariable("id") Long id, @ModelAttribute("addUserForm") @Valid AddUserForm addUserForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "pages/book/editParticipants";
        }

        Book book = getBook(id);
        if (!book.currentUserIsOwner() && !book.currentUserIsAdmin()) {
            throw new ForbiddenException();
        }

        User newParticipant = userService.findUserByEmail(addUserForm.getEmail());

        if (newParticipant == null) {
            // ToDo: show JS dialog to user that email was not found in db
            System.out.println("Could not find email in DB");
            return "pages/book/editParticipants";
        }

        if (book.userIsParticipant(newParticipant)) {
            // ToDo: show JS dialog that a user can only be added once
            System.out.println("User is already added to DB");
            return "pages/book/editParticipants";
        }

        Participation participation = new Participation(newParticipant, false);
        bookService.addParticipation(book, participation);
        return "redirect:/book/{id}/edit/participants";
    }
}
