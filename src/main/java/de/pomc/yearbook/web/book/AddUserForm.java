package de.pomc.yearbook.web.book;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

public class AddUserForm {

    @Getter
    @Setter
    @Email(message = "Bitte geben Sie eine Email-Adresse an")
    private String email = "";
}
