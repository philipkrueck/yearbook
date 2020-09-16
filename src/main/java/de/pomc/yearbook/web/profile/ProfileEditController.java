package de.pomc.yearbook.web.profile;

import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.participation.ParticipationService;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserService;
import de.pomc.yearbook.web.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/profile/editProfile")
@RequiredArgsConstructor
public class ProfileEditController {

    private final UserService userService;
    private final BookService bookService;
    private final ParticipationService participationService;

   @ModelAttribute("user")
    public User getCurrentUser() {
        User user = userService.findCurrentUser(); // user must not be null

        if (user == null) {
            throw new ForbiddenException();
        }

        return user;
    }

    @PreAuthorize("authenticated")
    @GetMapping
    public String showEditProfileView(Model model) {
        User user = getCurrentUser();

        model.addAttribute("userImageForm", UserImageFormConverter.userImageForm(user));
        model.addAttribute("userForm", UserFormConverter.userForm(user));
        model.addAttribute("books", bookService.getBooksOfCurrentUser());
        model.addAttribute("participations", participationService.getParticipationsOfCurrentUser());

        return "pages/profile/editProfile";
    }

    @PostMapping("/edit")
    public String editProfile(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "pages/profile/editProfile";
        }

        User user = userService.findCurrentUser();
        UserFormConverter.update(user, userForm);

        return "redirect:/profile";
    }
}
