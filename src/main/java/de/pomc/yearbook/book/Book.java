package de.pomc.yearbook.book;

import de.pomc.yearbook.participation.Participation;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.utils.ConvertByte;
import lombok.*;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
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

    @Lob
    private byte[] image;

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

    // questions can only be deleted if no participant filled in an answer to that question
    public boolean questionCanNotBeDeletedAt(int index) {
        return participations
                .stream()
                .anyMatch(participation -> (participation.getAnswers().size() > index && participation.getAnswers().get(index) != null));
    }

    public String getImageBase64() throws UnsupportedEncodingException {
        ConvertByte convertByte = new ConvertByte();
        return convertByte.ToBase64(getImage());
    }
}
