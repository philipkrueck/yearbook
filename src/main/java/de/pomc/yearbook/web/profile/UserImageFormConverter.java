package de.pomc.yearbook.web.profile;

import de.pomc.yearbook.user.User;

public abstract class UserImageFormConverter {

    public static UserImageForm userImageForm(User user) {

        byte[] image = user.getImage();

        return new UserImageForm(image);
    }

    public static User update(User user, UserImageForm userImageForm) {

        user.setImage(userImageForm.getImage());

        return user;
    }
}
