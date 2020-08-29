package de.pomc.yearbook.book;

import de.pomc.yearbook.user.User;
import lombok.Getter;

import java.util.List;

public class Participation {

    @Getter
    private Long id;

    @Getter
    private User participant;

    @Getter
    private List<String> anwers;

    @Getter
    private Book book;



    @Getter
    private boolean isAdmin;



    public Participation(Long id, User participant, boolean isAdmin) {
        this.id = id;
        this.participant = participant;
        this.isAdmin = isAdmin;
    }

    public boolean currentUserIsOwner() {
        return User.getCurrentUsername().equals(participant.getEmail());
    }
}
