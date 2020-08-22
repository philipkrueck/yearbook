package de.pomc.yearbook.web.book;

import de.pomc.yearbook.web.questionnaire.QuestionViewModel;
import lombok.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class BookViewModel {

    @Getter
    private Long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    private boolean currentUserIsOwner;

    // TODO: add image

    public BookViewModel(String title, String description, boolean currentUserIsOwner) {
        this.title = title;
        this.description = description;
        this.currentUserIsOwner = currentUserIsOwner;
    }
}
