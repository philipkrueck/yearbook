package de.pomc.yearbook.web.questionnaire;

import lombok.Getter;
import lombok.Setter;

public class CommentForm {
    @Getter
    private String comment;

    public void setComment(String comment) {
        this.comment = comment;
    }
}
