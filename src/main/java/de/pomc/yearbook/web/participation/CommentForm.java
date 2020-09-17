package de.pomc.yearbook.web.participation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
public class CommentForm {
    @Setter
    @Getter
    @NotBlank(message = "Feld darf nicht leer sein.")
    @Size(min = 5, message = "Der Kommentar muss mindestens 5 Zeichen lang sein.")
    private String comment = "";
}
