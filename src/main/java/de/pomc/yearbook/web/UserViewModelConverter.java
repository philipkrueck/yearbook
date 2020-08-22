package de.pomc.yearbook.web;

import de.pomc.yearbook.user.User;
import de.pomc.yearbook.web.UserViewModel;

public abstract class UserViewModelConverter {

    public static UserViewModel userViewModel(User user) {
        return new UserViewModel(user.getName(), user.getEmail());
    }
}
