package de.pomc.yearbook.web.profile;

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

import javax.validation.Valid;

@Controller
@RequestMapping("/profile/changePassword")
@RequiredArgsConstructor
public class ChangePasswordController {

    private final UserService userService;
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
    public String showChangePasswordView(Model model) {
        User user = getCurrentUser();
        model.addAttribute("changePasswordForm", new ChangePasswordForm());
        return "pages/profile/changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute("changePasswordForm") @Valid ChangePasswordForm changePasswordForm, BindingResult bindingResult) {
        User user = getCurrentUser();

        if(bindingResult.hasErrors()
            || (!changePasswordForm.getNewPasswordOne().equals(changePasswordForm.getNewPasswordTwo()))
            || (!passwordEncoder.matches(changePasswordForm.getOldPasword(), user.getPassword()))){
            return "pages/profile/changePassword";
        }

        user.setPassword(passwordEncoder.encode(changePasswordForm.getNewPasswordOne()));
        userService.save(user);

        return "redirect:/profile";
    }
}
