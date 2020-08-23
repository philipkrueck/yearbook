package de.pomc.yearbook.web.profile;

import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserService;
import de.pomc.yearbook.web.UserViewModelConverter;
import de.pomc.yearbook.web.exceptions.ForbiddenException;
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

    @PreAuthorize("authenticated")
    @GetMapping
    public String showProfile(Model model) {
        User user = userService.findCurrentUser(); // user must not be null

        UserViewModel userViewModel = UserViewModelConverter.userViewModel(user);

        model.addAttribute("userViewModel", userViewModel);
        model.addAttribute("profileViewModel", new ProfileViewModel("petergriffin", "peter.griffin@gmail.com", "Hi, I'm Peter.", ProfileBookViewModel.sampleData, ProfileParticipationViewModel.sampleData));

        return "pages/profile/profile";
    }

    @PostMapping("/edit")
    public String editProfile(@ModelAttribute("profileViewModel") UserViewModel userViewModel) {

        User user = userService.findCurrentUser();

        user.setName(userViewModel.getName());
        user.setEmail(userViewModel.getEmail());
        user.setTwitterHandle(userViewModel.getTwitterHandle());
        user.setLocation(userViewModel.getLocation());
        user.setWebsite(userViewModel.getWebsite());
        user.setBio(userViewModel.getBio());

        userService.save(user);

        return "redirect:/profile";
    }
}
