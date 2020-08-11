package de.pomc.yearbook.web;

import de.pomc.yearbook.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("indexViewModel", new IndexViewModel(FeaturedBookViewModel.sampleData));
        return "index";
    }

    @GetMapping("/login")
    public String showLoginView() { return "pages/login/login"; }
}
