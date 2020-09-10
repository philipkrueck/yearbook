package de.pomc.yearbook.web.participation;

import de.pomc.yearbook.book.Question;
import de.pomc.yearbook.participation.Answer;
import de.pomc.yearbook.participation.Participation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@AllArgsConstructor
public class EditAnswersForm {

    @Getter
    @Setter
    private List<String> answers;
}
