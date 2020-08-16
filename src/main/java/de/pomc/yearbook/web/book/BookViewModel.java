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

    // TODO: add image

    public BookViewModel(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
