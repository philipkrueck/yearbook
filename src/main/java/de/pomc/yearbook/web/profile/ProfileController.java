package de.pomc.yearbook.web.profile;

import de.pomc.yearbook.web.exceptions.ForbiddenException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping
    public String showProfile() {
        boolean loggedIn = false;

        if (!loggedIn) { // TODO: actually check if the user is logged in or not
            throw new ForbiddenException();
        }

        return "pages/profile/profile";
    }
}
