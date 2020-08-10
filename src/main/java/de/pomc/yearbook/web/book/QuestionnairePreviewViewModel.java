package de.pomc.yearbook.web.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
class QuestionnairePreviewViewModel {

    @Getter
    @Setter
    private String title;

    // TODO: Add image

    static List<QuestionnairePreviewViewModel> sampleData = List.of(
        new QuestionnairePreviewViewModel("John Doe"),
        new QuestionnairePreviewViewModel("Peter Griffin"),
        new QuestionnairePreviewViewModel("Mat Fraser"),
        new QuestionnairePreviewViewModel("Ben Smith"),
        new QuestionnairePreviewViewModel("David Jones")
    );
}