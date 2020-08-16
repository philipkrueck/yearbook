package de.pomc.yearbook.web.book;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class EditParticipationsBookViewModel {

    @Getter
    private Long id;

    @Getter
    private String bookName;

    @Getter
    private List<ParticipationsViewModel> participants;

    @Getter
    @Setter
    private String newEmail;
}
