package de.pomc.yearbook.web.book;

import de.pomc.yearbook.user.User;
import lombok.Getter;

import java.util.List;

public class Participation {

    @Getter
    private User participant;

    @Getter
    private List<String> anwers;

    @Getter
    private boolean isOwner;

    public Participation(User participant, boolean isOwner) {
        this.participant = participant;
        this.isOwner = isOwner;
    }
}
