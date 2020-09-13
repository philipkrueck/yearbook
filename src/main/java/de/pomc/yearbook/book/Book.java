package de.pomc.yearbook.book;

import de.pomc.yearbook.participation.Participation;
import de.pomc.yearbook.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Book {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String description;

    @ManyToOne(optional = false)
    private User owner;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "book")
    private List<Question> questions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "book")
    private List<Participation> participations;

    @Basic(optional = false)
    private boolean published;

    // TODO: add image

    public Book(String name, String description, User owner, boolean published) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.published = published;
        this.questions = new ArrayList<>();
        this.participations = new ArrayList<>();
    }

    public boolean userHasParticipation(User user) {
        return participations
                .stream()
                .anyMatch(participation -> participation.getParticipant().getEmail().equals(user.getEmail()));
    }

    public boolean currentUserHasParticipation() {
        return participations
                .stream()
                .filter(participation -> participation.getParticipant().getEmail().equals(User.getCurrentUsername()))
                .findFirst()
                .orElse(null) != null;
    }

    public boolean isOwner(Participation participation) {
        return owner.getEmail().equals(participation.getParticipant().getEmail());
    }

    public boolean currentUserIsOwner() {
        return owner != null && this.owner.getEmail().equals(User.getCurrentUsername());
    }

    public boolean currentUserIsAdmin() {
        return participations.stream()
                    .anyMatch(participant -> participant.getParticipant().getEmail().equals(User.getCurrentUsername()) && participant.isAdmin());
    }

    public boolean currentUserCanDelete(int participationId) {
        if (participationId >= participations.size()) { return false; }
        Participation participationToDelete = participations.get(participationId);

        return currentUserIsOwner() || (!participationToDelete.isAdmin() && currentUserIsAdmin());
    }

    // questions can only be deleted if no participant filled in an answer to that question
    public boolean questionCanNotBeDeletedAt(int index) {
        return participations
                .stream()
                .anyMatch(participation -> (participation.getAnswers().size() > index && participation.getAnswers().get(index) != null));
    }
}
