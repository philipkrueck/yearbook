package de.pomc.yearbook.web.participation;

import de.pomc.yearbook.book.Question;
import de.pomc.yearbook.participation.Answer;
import de.pomc.yearbook.participation.Participation;

import java.util.ArrayList;
import java.util.List;

public final class EditAnswersFormConverter {

    public static EditAnswersForm editAnswersForm(Participation participation) {
        List<Question> questions = participation.getBook().getQuestions();
        List<Answer> participationAnswers = participation.getAnswers();
        List<String> answers = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            if (i >= participationAnswers.size() || participationAnswers.get(i) == null) {
                answers.add("");
            } else {
                answers.add(participationAnswers.get(i).getAnswer());
            }
        }

        return new EditAnswersForm(answers);
    }

    public static List<Answer> getAnswers(EditAnswersForm answersForm, Participation participation) {
        List<Answer> answers = new ArrayList<>();

        for (int i = 0; i < answersForm.getAnswers().size(); i++) {
            answers.add(new Answer(answersForm.getAnswers().get(i)));
        }

        return answers;
    }
}
