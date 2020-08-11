package de.pomc.yearbook.web.questionnaire;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class QuestionViewModel {

    @Getter
    @Setter
    private String question;

    @Getter
    @Setter
    private String answer;


    public static List<QuestionViewModel> sampleData = List.of(
        new QuestionViewModel("What is your favorite movie?", "Interstellar"),
        new QuestionViewModel("Who did you spend the most time with?", "John Doe"),
        new QuestionViewModel("What was your most embarrassing moment?", "Too embarrassed to tell.")
    );
}
