package de.pomc.yearbook.web.users;

import de.pomc.yearbook.SampleData;
import de.pomc.yearbook.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UsersController {

    @GetMapping
    public String getUsers(Model model) {
        List<User> userViewModels = SampleData.getUsers();

        model.addAttribute("userViewModels", userViewModels);

        return "pages/users/show";
    }
}
