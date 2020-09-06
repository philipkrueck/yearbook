package de.pomc.yearbook.web.book;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class AddUserForm {

    @Getter
    @Setter
    @NotEmpty(message = "Das Feld darf nicht leer sein.")
    @Email(message = "Bitte geben Sie eine Email-Adresse ein.")
    private String email = "";
}
