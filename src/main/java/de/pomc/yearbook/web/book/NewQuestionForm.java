package de.pomc.yearbook.web.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class NewQuestionForm {

    @Getter
    @Setter
    @NotEmpty(message = "Feld darf nicht leer sein.")
    private String question = "";
}
