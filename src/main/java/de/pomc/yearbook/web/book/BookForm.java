package de.pomc.yearbook.web.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BookForm {
    private String name;
    private String description;

    public BookForm() {
        this.name = "";
        this.description = "";
    }
}
