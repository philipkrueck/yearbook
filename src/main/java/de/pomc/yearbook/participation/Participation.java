package de.pomc.yearbook.participation;

import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
public class Participation {

    @Id
    @Getter
    @GeneratedValue
    private Long id;

    @Getter
    @ManyToOne(optional = false)
    private User participant;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "participation")
    private List<Answer> answers;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    private Book book;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "participation")
    private List<Comment> comments = new ArrayList<>();

    @Getter
    @Setter
    @Basic(optional = false)
    private boolean isAdmin;

    public Participation(User participant, boolean isAdmin) {
        this.participant = participant;
        this.isAdmin = isAdmin;
        this.answers = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public boolean currentUserIsParticipant() {
        return User.getCurrentUsername() != null && User.getCurrentUsername().equals(participant.getEmail());
    }

    public boolean currentUserCanComment() {
        return book.currentUserHasParticipation();
    }

    public List<Integer> getNonBlankAnswerIndices() {
        List<Integer> nonBlankAnswerIndices = new ArrayList<>();
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            if (!(answer == null) && !answer.getAnswer().isBlank()) {
                nonBlankAnswerIndices.add(i);
            }
        }
        return nonBlankAnswerIndices;
    }
}
