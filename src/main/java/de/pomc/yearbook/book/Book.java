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

    public boolean userIsParticipant(User user) {
        for (Participation participation: participations) {
            if (participation.getParticipant().getEmail().equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public boolean currentUserIsParticipant() {
        return participations
                .stream()
                .filter(participation -> participation.getParticipant().getEmail().equals(User.getCurrentUsername()))
                .findFirst()
                .orElse(null) != null;
    }

    public boolean currentUserIsOwner() {
        return owner != null && this.owner.getEmail().equals(User.getCurrentUsername());
    }

    public boolean currentUserIsAdmin() {
        return participations.stream()
                    .anyMatch(participant -> participant.getParticipant().getEmail().equals(User.getCurrentUsername()) && participant.isAdmin());
    }

    public boolean currentUserCanDelete(int participationId) {
        Participation participationToDelete = participations.get(participationId);
        Participation participationCurrentUser = participations.stream()
                                                                .filter(Participation::currentUserIsParticipant)
                                                                .findFirst().orElse(null);

        if (participationCurrentUser == null || participationToDelete == null) {
            return false;
        }

        return currentUserIsOwner() || (!participationToDelete.isAdmin() && participationCurrentUser.isAdmin());
    }

    public boolean isOwner(Participation participation) {
        return owner.getEmail().equals(participation.getParticipant().getEmail());
    }
}
