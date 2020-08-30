package de.pomc.yearbook.web.profile;

import de.pomc.yearbook.user.User;
import de.pomc.yearbook.web.profile.UserForm;

public abstract class UserFormConverter {

    public static UserForm userForm(User user) {
        String firstName = user.getFirstName();
        String email = user.getEmail();
        String twitterHandle = user.getTwitterHandle() != null ? user.getTwitterHandle() : "";
        String location = user.getLocation() != null ? user.getLocation() : "";
        String website = user.getWebsite() != null ? user.getWebsite() : "";
        String bio = user.getBio();

        return new UserForm(firstName, email, twitterHandle, location, website, bio);
    }
}
