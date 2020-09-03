package de.pomc.yearbook.web.profile;

import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserService;
import de.pomc.yearbook.web.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final BookService bookService;
    private final PasswordEncoder passwordEncoder;

    private User getCurrentUser() {
        User user = userService.findCurrentUser(); // user must not be null

        if (user == null) {
            throw new ForbiddenException();
        }

        return user;
    }

    @SneakyThrows
    @PreAuthorize("authenticated")
    @GetMapping
    public String showProfile(Model model) {
        User user = getCurrentUser();

        byte[] encodeBase64 = Base64.encode(user.getImage());
        String base64Encoded = new String(encodeBase64, "UTF-8");

        model.addAttribute("userForm", UserFormConverter.userForm(user));
        model.addAttribute("user", user);
        model.addAttribute("userImage", base64Encoded);
        model.addAttribute("books", bookService.getBooksOfCurrentUser());
        model.addAttribute("participations", bookService.getParticipationsOfCurrentUser());
        model.addAttribute("changePasswordForm", new ChangePasswordForm());

        return "pages/profile/profile";
    }

    @SneakyThrows
    @PostMapping("/edit")
    public String editProfile(final @ModelAttribute("userForm") @Valid UserForm userForm, BindingResult bindingResult, final @RequestParam("image") MultipartFile image) {

        // ToDo: Validate userForm
        User user = getCurrentUser();

        userForm.setImage(image.getBytes());

        UserFormConverter.update(user, userForm);

        userService.save(user);

        return "redirect:/profile";
    }

    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute("changePasswordForm") ChangePasswordForm changePasswordForm) {
        // ToDo: validate ChangePasswordForm

        User user = getCurrentUser();

        // Maybe we can add this logic to the form validation?
        if (!changePasswordForm.getNewPasswordOne().equals(changePasswordForm.getNewPasswordTwo())) {
            return "redirect:/profile#changePassword";
        }

        if (!passwordEncoder.matches(changePasswordForm.getOldPasword(), user.getPassword())) {
            return "redirect:/profile#changePassword";
        }

        user.setPassword(passwordEncoder.encode(changePasswordForm.getNewPasswordOne()));
        userService.save(user);

        return "redirect:/profile#changedPasswordSuccessfully";
    }
}
