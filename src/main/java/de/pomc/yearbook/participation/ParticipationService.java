package de.pomc.yearbook.participation;

import de.pomc.yearbook.SampleData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipationService {

    public Participation getParticipationWithID(Long id) {
        return SampleData.participations
                .stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Comment addComment(Comment comment) {
        List<Comment> comments = new ArrayList<>(SampleData.getComments());
        comments.add(comment);
        SampleData.setComments(comments);

        return comment;
    }

    public Participation save(Participation participation) {
        return participation;
    }
}
