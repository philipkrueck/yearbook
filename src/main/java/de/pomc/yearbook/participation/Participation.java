package de.pomc.yearbook.participation;

import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Participation {

    @Getter
    private Long id;

    @Getter
    private User participant;

    @Getter
    @Setter
    private List<String> answers = new ArrayList<>();

    @Getter
    private Book book;

    @Getter
    private List<Comment> comments = new ArrayList<>();

    @Getter
    private boolean isAdmin;

    public Participation(Long id, User participant, boolean isAdmin, List<String> answers, List<Comment> comments) {
        this.id = id;
        this.participant = participant;
        this.isAdmin = isAdmin;
        this.answers = answers;
        this.comments = comments;
    }

    public boolean currentUserIsOwner() {
        return User.getCurrentUsername().equals(participant.getEmail());
    }

    public List<Integer> getNonBlankAnswerIndices() {
        List<Integer> nonBlankAnswerIndices = new ArrayList<>();
        for (int i = 0; i < answers.size(); i++) {
            String answer = answers.get(i);
            if (!(answer == null) &&  !answer.isBlank()) {
                nonBlankAnswerIndices.add(i);
            }
        }

        return nonBlankAnswerIndices;
    }
}
