package de.pomc.yearbook.web.profile;

import de.pomc.yearbook.web.exceptions.ForbiddenException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping
    public String showProfile(Model model) {
        boolean loggedIn = true;

        model.addAttribute("profileViewModel", new ProfileViewModel("petergriffin", "peter.griffin@gmail.com", "Hi, I'm Peter."));

        if (!loggedIn) { // TODO: actually check if the user is logged in or not
            throw new ForbiddenException();
        }

        return "pages/profile/profile";
    }

    @PostMapping("/edit")
    public String editProfile(@ModelAttribute("profileViewModel") ProfileViewModel profileViewModel) {

        System.out.println("bio:" + profileViewModel.getBio());
        System.out.println("email:" + profileViewModel.getEmail());
        System.out.println("username:" + profileViewModel.getUsername());

        return "redirect:/profile";
    }
}
