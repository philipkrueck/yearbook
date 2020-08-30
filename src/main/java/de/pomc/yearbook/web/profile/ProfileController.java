package de.pomc.yearbook.web.profile;

import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final BookService bookService;

    @PreAuthorize("authenticated")
    @GetMapping
    public String showProfile(Model model) {
        User user = userService.findCurrentUser(); // user must not be null

        model.addAttribute("userForm", UserFormConverter.userForm(user));
        model.addAttribute("user", user);
        model.addAttribute("books", bookService.getBooksOfCurrentUser());
        model.addAttribute("participations", bookService.getParticipationsOfCurrentUser());
        model.addAttribute("changePasswordForm", new ChangePasswordForm());

        return "pages/profile/profile";
    }

    @PostMapping("/edit")
    public String editProfile(@ModelAttribute("userForm") @Valid  UserForm userForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "redirect:/profile";
        }

        User user = userService.findCurrentUser();

        if(userService.findUserByEmail(userForm.getEmail()) != null && !user.getEmail().equals(userForm.getEmail())){
            return "redirect:/profile?EmailExists";
        }

        UserFormConverter.update(user, userForm);

        userService.save(user);

        return "redirect:/profile";
    }
}
