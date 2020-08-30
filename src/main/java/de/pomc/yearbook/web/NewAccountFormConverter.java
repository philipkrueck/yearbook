package de.pomc.yearbook.web;

import de.pomc.yearbook.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class NewAccountFormConverter {

    public static User userFromForm(NewAccountForm form, PasswordEncoder passwordEncoder) {
        return new User(form.getFirstName(), form.getLastName(), form.getEmail(), passwordEncoder.encode(form.getPassword()));
    }
}
