package de.pomc.yearbook.web.users;

import de.pomc.yearbook.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "pages/users/show";
    }
}
