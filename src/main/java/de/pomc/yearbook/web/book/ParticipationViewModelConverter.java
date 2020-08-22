package de.pomc.yearbook.web.book;

import de.pomc.yearbook.web.UserViewModel;
import de.pomc.yearbook.web.UserViewModelConverter;

public class ParticipationViewModelConverter {

    public static ParticipationViewModel participationViewModel(Participation participation) {
        UserViewModel userViewModel = UserViewModelConverter.userViewModel(participation.getParticipant());
        return new ParticipationViewModel(userViewModel, participation.isOwner());
    }
}
