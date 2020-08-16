package de.pomc.yearbook.web.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Book {

    @Getter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private List <String> questions;

    @Getter
    @Setter
    private List <Participation> participations;

    // TODO: add image

    public Book(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.questions = new ArrayList<>();
        this.participations = new ArrayList<>();
    }
}
