package de.pomc.yearbook.web.participation;

import de.pomc.yearbook.book.Question;
import de.pomc.yearbook.participation.Answer;
import de.pomc.yearbook.participation.Participation;
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

    public EditAnswersForm(Participation participation) {
        List<Question> questions = participation.getBook().getQuestions();
        List<Answer> participationAnswers = participation.getAnswers();

        for (int i = 0; i < questions.size(); i++) {
            if (i >= participationAnswers.size() || participationAnswers.get(i) == null) {
                answers.set(i, "");
            } else {
                answers.set(i, participationAnswers.get(i).getAnswer());
            }
        }
    }
}
