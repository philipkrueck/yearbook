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
    @Transient // ToDo: add relationship here
    private List<String> answers = new ArrayList<>();

    @Getter
    @Setter
    @ManyToOne(optional = false)
    private Book book;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "participation") // ToDo: add relationship here
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


    // ToDo: remove this constructor, once dummy data is gone
    public Participation(Long id, User participant, boolean isAdmin, List<String> answers, List<Comment> comments) {
        this.id = id;
        this.participant = participant;
        this.isAdmin = isAdmin;
        this.answers = answers;
        this.comments = comments;
    }

    public boolean currentUserIsOwner() {
        return User.getCurrentUsername().equals(participant.getEmail());
    }

    public List<Integer> getNonBlankAnswerIndices() {
        List<Integer> nonBlankAnswerIndices = new ArrayList<>();
        for (int i = 0; i < answers.size(); i++) {
            String answer = answers.get(i);
            if (!(answer == null) &&  !answer.isBlank()) {
                nonBlankAnswerIndices.add(i);
            }
        }

        return nonBlankAnswerIndices;
    }
}
