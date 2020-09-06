package de.pomc.yearbook.book;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Basic(optional = false)
    private String question;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    private Book book;

    public Question(String question) {
        this.question = question;
    }
}
