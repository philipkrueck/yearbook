package de.pomc.yearbook.web.profile;

import de.pomc.yearbook.user.User;
import de.pomc.yearbook.web.profile.UserForm;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public abstract class UserFormConverter {

    public static UserForm userForm(User user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String twitterHandle = user.getTwitterHandle() != null ? user.getTwitterHandle() : "";
        String location = user.getLocation() != null ? user.getLocation() : "";
        String website = user.getWebsite() != null ? user.getWebsite() : "";
        String bio = user.getBio();

        return new UserForm(firstName, lastName, twitterHandle, location, website, bio);
    }

    public static User update(User user, UserForm userForm) {
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setTwitterHandle(userForm.getTwitterHandle());
        user.setLocation(userForm.getLocation());
        user.setWebsite(userForm.getWebsite());
        user.setBio(userForm.getBio());
        return user;
    }
}
