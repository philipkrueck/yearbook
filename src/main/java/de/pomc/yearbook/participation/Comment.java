package de.pomc.yearbook.participation;

import de.pomc.yearbook.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Comment {

    @Getter
    @Setter
    private String comment;

    @Getter
    private User author;

    public boolean authorIsCurrentUser() {
        String currentUsername = User.getCurrentUsername();
        if (currentUsername == null) {
            return false;
        }
        return currentUsername.equals(author.getEmail());
    }
}
