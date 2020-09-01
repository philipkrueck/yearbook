package de.pomc.yearbook.web.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class NewQuestionForm {

    @Getter
    @Setter
    @NotBlank(message = "Feld darf nicht leer sein.")
    private String question = "";
}
