package de.pomc.yearbook.web.participation;

import de.pomc.yearbook.participation.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EditAnswersForm {

    @Getter
    @Setter
    private List<String> answers;

    public EditAnswersForm(List<Answer> answers) {
        this.answers = answers.stream().map(Answer::getAnswer).collect(Collectors.toList());
    }
}
