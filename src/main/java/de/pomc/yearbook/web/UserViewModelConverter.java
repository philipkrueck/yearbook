package de.pomc.yearbook.web;

import de.pomc.yearbook.user.User;
import de.pomc.yearbook.web.profile.UserViewModel;

public abstract class UserViewModelConverter {

    public static UserViewModel userViewModel(User user) {
        String name = user.getName();
        String email = user.getEmail();
        String twitterHandle = user.getTwitterHandle() != null ? user.getTwitterHandle() : "";
        String location = user.getLocation() != null ? user.getLocation() : "";
        String website = user.getWebsite() != null ? user.getWebsite() : "";
        String bio = user.getBio();

        return new UserViewModel(name, email, twitterHandle, location, website, bio);
    }
}
