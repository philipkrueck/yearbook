package de.pomc.yearbook.web.book;

import de.pomc.yearbook.book.Participation;
import de.pomc.yearbook.web.profile.UserViewModel;
import de.pomc.yearbook.web.UserViewModelConverter;

public class ParticipationViewModelConverter {

    public static ParticipationViewModel participationViewModel(Participation participation) {
        UserViewModel userViewModel = UserViewModelConverter.userViewModel(participation.getParticipant());
        return new ParticipationViewModel(userViewModel, participation.isOwner());
    }
}
