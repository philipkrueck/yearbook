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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import javax.validation.Valid;

import javax.swing.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final BookService bookService;
    private final ParticipationService participationService;
    private final PasswordEncoder passwordEncoder;

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
    public String showProfile(Model model) {
        User user = getCurrentUser();

        model.addAttribute("userImageForm", UserImageFormConverter.userImageForm(user));
        model.addAttribute("userForm", UserFormConverter.userForm(user));
        model.addAttribute("books", bookService.getBooksOfCurrentUser());
        model.addAttribute("participations", participationService.getParticipationsOfCurrentUser());
        model.addAttribute("changePasswordForm", new ChangePasswordForm());

        return "pages/profile/profile";
    }

    @PostMapping("/addProfileImage")
    public String addProfileImage(final @RequestParam("image") MultipartFile image) {
       try {
           User user = getCurrentUser();
           user.setImage(image.getBytes());
           userService.save(user);
           return "redirect:/";
       }
       catch (IOException e) {
         throw new ForbiddenException(); //TODO: Change to SomethingWentWrongException
        }
    }

    @PostMapping("/edit")
    public String editProfile(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "pages/profile/profile";
        }

        User user = userService.findCurrentUser();
        UserFormConverter.update(user, userForm);

        return "redirect:/profile";
    }

    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute("changePasswordForm") @Valid ChangePasswordForm changePasswordForm, BindingResult bindingResult) {
        User user = getCurrentUser();

        // ToDo: implement form validation and show js dialog accordingly
        //if(bindingResult.hasErrors()
        //    || (!changePasswordForm.getNewPasswordOne().equals(changePasswordForm.getNewPasswordTwo()))
        //    || (!passwordEncoder.matches(changePasswordForm.getOldPasword(), user.getPassword()))){
        //    return "pages/profile/profile#changePassword";
        //}

        user.setPassword(passwordEncoder.encode(changePasswordForm.getNewPasswordOne()));
        userService.save(user);

        return "redirect:/profile#changedPasswordSuccessfully";
    }
}
