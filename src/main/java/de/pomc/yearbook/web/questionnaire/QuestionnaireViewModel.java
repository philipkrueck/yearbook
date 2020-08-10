package de.pomc.yearbook.web.questionnaire;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class QuestionnaireViewModel {
    @Getter
    @Setter
    private String bookName;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private List<QuestionViewModel> questionViewModels;

    @Getter
    @Setter
    private List<CommentViewModel> commentViewModels;
}
