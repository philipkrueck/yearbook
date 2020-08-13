package de.pomc.yearbook.web.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class BookViewModel {

    // TODO: add image

    @Getter
    private Long id;

    @Getter
    private String title;

    @Getter
    private String description;

    @Getter
    private List<QuestionnairePreviewViewModel> questionnairePreviewViewModels;


    @Getter
    private static Map<Long, BookViewModel> sampleData = Map.of(
            (long) 1, new BookViewModel((long) 1, "Blue Mountain State 2020", "some description", QuestionnairePreviewViewModel.sampleData),
            (long) 2, new BookViewModel((long) 2, "HSBA BI '21", "some description", QuestionnairePreviewViewModel.sampleData),
            (long) 3, new BookViewModel((long) 3, "Stanford Law '19", "some description", QuestionnairePreviewViewModel.sampleData),
            (long) 4, new BookViewModel((long) 4, "MIT Robotics 2020", "some description", QuestionnairePreviewViewModel.sampleData),
            (long) 5, new BookViewModel((long) 5, "NYU Gender Sciences 2019", "some description", QuestionnairePreviewViewModel.sampleData)
    );

}
