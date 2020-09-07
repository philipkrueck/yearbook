package de.pomc.yearbook.participation;

import de.pomc.yearbook.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationRepository participationRepository;

    public Participation save(Participation participation) {
        return participationRepository.save(participation);
    }

    public List<Participation> findAll() {
        return participationRepository.findAll();
    }

    public void addComment(Comment comment, Participation participation) {
        comment.setParticipation(participation);
        participation.getComments().add(comment);
    }

    public void setAnswers(Participation participation, List<Answer> answers) {
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            answer.setParticipation(participation);
            answer.setQuestion(participation.getBook().getQuestions().get(i));
            participation.setAnswers(answers);
        }
    }

    public Participation getParticipationWithID(Long id) {
        return participationRepository.findById(id).orElse(null);
    }

    public List<Participation> getParticipationsOfCurrentUser() {
        return participationRepository.findParticipationByParticipant_Email(User.getCurrentUsername());
    }
}
