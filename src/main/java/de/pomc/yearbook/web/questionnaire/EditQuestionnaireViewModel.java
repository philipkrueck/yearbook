package de.pomc.yearbook.web.questionnaire;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class EditQuestionnaireViewModel {

    @Getter
    @Setter
    private String bookName;

    @Getter
    @Setter
    private List<QuestionViewModel> questionViewModels;
}
