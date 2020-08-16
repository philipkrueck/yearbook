package de.pomc.yearbook.user;

public abstract class UserViewModelConverter {

    public static UserViewModel userViewModel(User user) {
        return new UserViewModel(user.getName(), user.getEmail());
    }
}
