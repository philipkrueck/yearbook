package de.pomc.yearbook.web;

import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserAdapter;
import de.pomc.yearbook.user.UserAdapterService;
import de.pomc.yearbook.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;
    private final UserAdapterService userAdapterService;
    private final BookService bookService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String index(Model model) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return "redirect:/profile";
        }

        model.addAttribute("newAccountForm", new NewAccountForm());
        model.addAttribute("publishedBooks", bookService.getPublishedBooks());
        return "index";
    }

    @GetMapping("/login")
    public String showLoginView() { return "pages/login/login"; }

    @PostMapping("newAccount")
    public String createAccount(@ModelAttribute("newAccountForm") NewAccountForm newAccountForm) {
        // ToDo: Validate NewAccountForm

        // ToDo: Make sure no user with the same email exists
        User user = NewAccountFormConverter.userFromForm(newAccountForm, passwordEncoder);
        userService.save(user);

        UserDetails userDetails = userAdapterService.loadUserByUsername(newAccountForm.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:";
    }
}
