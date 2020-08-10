package de.pomc.yearbook.web.questionnaire;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class CommentViewModel {
    @Getter
    @Setter
    private String comment;

    @Getter
    @Setter
    private String author;

    public static List<CommentViewModel> sampleData = List.of(
        new CommentViewModel("Great guy!", "John Doe"),
        new CommentViewModel("Genius", "Mat Fraser")
    );

}
