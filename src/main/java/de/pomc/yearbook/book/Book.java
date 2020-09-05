package de.pomc.yearbook.book;

import de.pomc.yearbook.participation.Participation;
import de.pomc.yearbook.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    // TODO: Add relationship here
    @Transient
    private List<String> questions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "book")
    private Set<Participation> participations;

    @Basic(optional = false)
    private boolean published;

    // TODO: add image

    public Book(String name, String description, User owner) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.published = false;
        this.questions = new ArrayList<>();
        this.participations = new HashSet<>();
    }

    // ToDo: remove this init once SampleData is gone
    public Book(Long id, String name, String description, User owner, boolean published) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.published = published;
        this.questions = new ArrayList<>();
        // this.participations = new ArrayList<>();
    }

    // ToDo: remove this init once SampleData is gone
    public Book(Long id, String name, String description, User owner, List<String> questions, boolean published) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.published = published;
        this.questions = new ArrayList<>();
        // this.participations = new ArrayList<>();
    }

    public boolean currentUserIsOwner() {
        return owner != null && this.owner.getEmail().equals(User.getCurrentUsername());
    }

    public boolean currentUserIsAdmin() {
        return true;
                //participations.stream().
                //anyMatch(participant -> participant.getParticipant().getEmail().equals(User.getCurrentUsername()) && participant.isAdmin());
    }
}
