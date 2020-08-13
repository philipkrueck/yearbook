package de.pomc.yearbook.web.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
class QuestionnairePreviewViewModel {

    @Getter
    private Long id;

    @Getter
    private String title;

    // TODO: Add image

    static List<QuestionnairePreviewViewModel> sampleData = List.of(
        new QuestionnairePreviewViewModel((long) 1, "John Doe"),
        new QuestionnairePreviewViewModel((long) 2, "Peter Griffin"),
        new QuestionnairePreviewViewModel((long) 3, "Mat Fraser"),
        new QuestionnairePreviewViewModel((long) 4, "Ben Smith"),
        new QuestionnairePreviewViewModel((long) 5, "David Jones")
    );
}