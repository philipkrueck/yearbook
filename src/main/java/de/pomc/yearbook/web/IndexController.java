package de.pomc.yearbook.web;

import de.pomc.yearbook.SampleData;
import de.pomc.yearbook.user.UserService;
import de.pomc.yearbook.web.book.BookViewModel;
import de.pomc.yearbook.web.book.BookViewModelConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return "redirect:/profile";
        }


        List<BookViewModel> bookViewModels = SampleData.getBooks()
                                                        .stream()
                                                        .map(BookViewModelConverter::bookViewModel)
                                                        .collect(Collectors.toList());

        model.addAttribute("bookViewModels", bookViewModels);
        return "index";
    }

    @GetMapping("/login")
    public String showLoginView() { return "pages/login/login"; }
}
