package de.pomc.yearbook.web.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class BookViewModel {

    // TODO: add image

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private List<QuestionnairePreviewViewModel> questionnairePreviewViewModels;
}
