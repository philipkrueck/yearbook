package de.pomc.yearbook.web;

import de.pomc.yearbook.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class NewAccountForm {

    @NotEmpty(message = "Feld darf nicht leer sein.")
    private String firstName;
    @NotEmpty(message = "Feld darf nicht leer sein.")
    private String lastName;
    @Email(message = "Bitte geben Sie eine Email-Adresse ein.")
    private String email;
    @Pattern(
            regexp = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})",
            message = "Das Passwort muss zwischen 8 und 40 Zeichen lang sein, " +
                    "mindestens eine Zahl, " +
                    "einen Kleinbuchstabend, " +
                    "einen Großbuchstabend und " +
                    "ein Sonderzeichen enthalten."
    )
    private String password;

    public NewAccountForm() {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
    }
}