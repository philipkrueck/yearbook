package de.pomc.yearbook.web.profile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class ChangePasswordForm {

    @NotBlank(message = "Das Feld darf nicht leer sein.")
    String oldPasword = "";

    @Pattern(
            regexp = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})",
            message = "Das Passwort muss zwischen 8 und 40 Zeichen lang sein, " +
                    "mindestens eine Zahl, " +
                    "einen Kleinbuchstabend, " +
                    "einen Großbuchstabend und " +
                    "ein Sonderzeichen enthalten."
    )
    String newPasswordOne = "";

    @Pattern(
            regexp = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})",
            message = "Das Passwort muss zwischen 8 und 40 Zeichen lang sein, " +
                    "mindestens eine Zahl, " +
                    "einen Kleinbuchstabend, " +
                    "einen Großbuchstabend und " +
                    "ein Sonderzeichen enthalten."
    )
    String newPasswordTwo = "";
}
