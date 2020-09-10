package de.pomc.yearbook.web.profile;

import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.participation.ParticipationService;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserService;
import de.pomc.yearbook.web.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
import java.util.Base64;
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

    @SneakyThrows // is used here as the encoding must always succeed and would indicate a programming error otherwise
    @PreAuthorize("authenticated")
    @GetMapping
    public String showProfile(Model model) {
        User user = getCurrentUser();

        model.addAttribute("userForm", UserFormConverter.userForm(user));

        byte[] userImage = user.getImage();
        if (userImage != null && userImage.length > 0) {
            byte[] encodeBase64 = Base64.getEncoder().encode(user.getImage());
            String base64Encoded = new String(encodeBase64, "UTF-8");
            model.addAttribute("userImage", base64Encoded);
        }

        model.addAttribute("books", bookService.getBooksOfCurrentUser());
        model.addAttribute("participations", participationService.getParticipationsOfCurrentUser());
        model.addAttribute("changePasswordForm", new ChangePasswordForm());

        return "pages/profile/profile";
    }

    @SneakyThrows // ... is used here as the encoding must always succeed and would indicate a programming error otherwise
    @PostMapping("/addProfilePic")
    public String addProfilePic(final @RequestParam("image") MultipartFile image) {
        User user = getCurrentUser();

        // ToDo: image upload @Malte
        user.setImage(image.getBytes());
        userService.save(user);
        return "redirect:/";
    }

    @PostMapping("/edit")
    public String editProfile(final @ModelAttribute("userForm") @Valid UserForm userForm, BindingResult bindingResult) {

        // ToDo: implement form validation and show js dialog accordingly
        //if(bindingResult.hasErrors()){
        //    return "redirect:/profile";
        //}

        User user = userService.findCurrentUser();
        UserFormConverter.update(user, userForm);

        if(userService.findUserByEmail(userForm.getEmail()) != null && !user.getEmail().equals(userForm.getEmail())) {
            return "redirect:/profile?emailExists";
        }

        //TODO: add feature for new authentication if email was changed

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
