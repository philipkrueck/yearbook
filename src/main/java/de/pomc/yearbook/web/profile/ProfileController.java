package de.pomc.yearbook.web.profile;

import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        return "pages/profile/profile";
    }

    @PostMapping("/edit")
    public String editProfile(@ModelAttribute("profileViewModel") UserForm userForm) {

        User user = userService.findCurrentUser();

        user.setFirstName(userForm.getFirstName());
        user.setEmail(userForm.getEmail());
        user.setTwitterHandle(userForm.getTwitterHandle());
        user.setLocation(userForm.getLocation());
        user.setWebsite(userForm.getWebsite());
        user.setBio(userForm.getBio());

        userService.save(user);

        return "redirect:/profile";
    }
}
