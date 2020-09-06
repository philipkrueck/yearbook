package de.pomc.yearbook.participation;

import de.pomc.yearbook.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    private Participation participation;

    @Getter
    @Setter
    @Basic(optional = false)
    private String comment;

    @Getter
    @ManyToOne(optional = false)
    private User author;

    public Comment(String comment, User author) {
        this.comment = comment;
        this.author = author;
    }

    public boolean authorIsCurrentUser() {
        String currentUsername = User.getCurrentUsername();
        if (currentUsername == null) {
            return false;
        }
        return currentUsername.equals(author.getEmail());
    }
}
