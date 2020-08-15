package de.pomc.yearbook.web.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class CreateBookViewModel {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;
}
