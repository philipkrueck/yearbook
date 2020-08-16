package de.pomc.yearbook.web.book;

import de.pomc.yearbook.user.UserViewModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ParticipationViewModel {

    @Getter
    private UserViewModel userViewModel;

    @Getter
    public boolean isOwner;
}
