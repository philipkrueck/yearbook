package de.pomc.yearbook.web.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class EditQuestionsViewModel {

    @Getter
    private Long id;

    @Getter
    private String bookName;

    @Getter
    @Setter
    List<String> questions;

    @Getter
    @Setter
    private String newQuestion;


    public static List<String> sampleQuestions = List.of(
        "What was your best study moment?",
        "What was your favorite course"
    );
}
