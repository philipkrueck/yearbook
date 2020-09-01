package de.pomc.yearbook.web.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Getter
@Setter
public class UserForm {

    @NotBlank(message = "Feld darf nicht leer sein.")
    private String firstName;

    @NotBlank(message = "Feld darf nicht leer sein.")
    private String lastName;

    @Email(message = "Bitte geben Sie eine Email-Adresse ein.")
    private String email;

    //@Pattern(regexp = "^@")
    private String twitterHandle;

    private String location;

    @URL(message = "Bitte geben Sie eine gültige URL ein.")
    private String website;

    private String bio;
}
