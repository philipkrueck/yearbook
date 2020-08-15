package de.pomc.yearbook.web.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class EditQuestionsViewModel {

    @Getter
    private String bookName;

    @Getter
    @Setter
    List<String> questions;
}
