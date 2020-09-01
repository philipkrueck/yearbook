package de.pomc.yearbook.web.participation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class EditAnswersForm {

    @Getter
    @Setter
    private List<String> answers;
}
