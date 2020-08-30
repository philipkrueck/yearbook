package de.pomc.yearbook.web;

import de.pomc.yearbook.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
public class NewAccountForm {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public NewAccountForm() {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
    }
}