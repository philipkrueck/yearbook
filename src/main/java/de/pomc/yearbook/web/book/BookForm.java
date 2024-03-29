package de.pomc.yearbook.web.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
public class BookForm {

    @Size(min = 5, max = 50, message = "Der Name muss zwischen 5 und 50 Zeichen lang sein.")
    private String name;

    @Size(min = 5, max = 100, message = "Die Beschreibung muss zwischen 5 und 100 Zeichen lang sein.")
    private String description;

    private boolean published;

    public BookForm() {
        this.name = "";
        this.description = "";
    }
}
