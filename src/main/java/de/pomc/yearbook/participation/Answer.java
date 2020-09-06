package de.pomc.yearbook.participation;

import de.pomc.yearbook.book.Question;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Setter
    @Basic(optional = false)
    private String answer;

    @Getter
    @Setter
    @Basic(optional = false)
    @ManyToOne
    private Question question;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    private Participation participation;

    public Answer(String answer) {
        this.answer = answer;
    }
}
