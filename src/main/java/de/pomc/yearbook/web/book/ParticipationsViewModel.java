package de.pomc.yearbook.web.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class ParticipationsViewModel {

    @Getter
    private String name;

    @Getter
    private String email;

    @Getter
    private boolean isOwner;


    public static List<ParticipationsViewModel> sampleData = List.of(
        new ParticipationsViewModel("Frodo Baggins", "frodo.baggins@gmail.com", false),
        new ParticipationsViewModel("Sam Gamgee", "sam.gamgee@gmail.com", false)
    );
}
