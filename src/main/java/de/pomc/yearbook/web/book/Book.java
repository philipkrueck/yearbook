package de.pomc.yearbook.web.book;

import de.pomc.yearbook.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
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

    // TODO: add image

    public Book(Long id, String name, String description, User owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.questions = new ArrayList<>();
        this.participations = new ArrayList<>();
    }

    public boolean isOwnedByCurrentUser() {
        return owner != null && this.owner.getEmail().equals(User.getCurrentUsername());
    }
}
