package de.pomc.yearbook.web.profile;

import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.participation.ParticipationService;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserService;
import de.pomc.yearbook.web.exceptions.ForbiddenException;
import de.pomc.yearbook.web.exceptions.SomethingWentWrongException;
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
         throw new SomethingWentWrongException();
        }
    }
}
