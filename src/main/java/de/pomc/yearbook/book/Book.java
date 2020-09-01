package de.pomc.yearbook.book;

import de.pomc.yearbook.participation.Participation;
import de.pomc.yearbook.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Book {

    @Getter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @Getter
    private User owner;

    @Getter
    @Setter
    private List <String> questions;

    @Getter
    @Setter
    private List <Participation> participations;

    @Getter
    @Setter
    private boolean published;

    // TODO: add image

    public Book(Long id, String name, String description, User owner, boolean published) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.published = published;
        this.questions = new ArrayList<>();
        this.participations = new ArrayList<>();
    }

    public boolean currentUserIsOwner() {
        return owner != null && this.owner.getEmail().equals(User.getCurrentUsername());
    }

    public boolean currentUserIsAdmin() {
        return participations.stream().
                anyMatch(participant -> participant.getParticipant().getEmail().equals(User.getCurrentUsername()) && participant.isAdmin());
    }
}
