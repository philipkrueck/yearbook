package de.pomc.yearbook.web.book;

import lombok.Getter;

public class CreateBookForm extends BookForm {

    @Getter
    private byte[] image;

    public CreateBookForm(String name, String description, boolean published, byte[] image) {
        super(name, description, published);
        this.image = image;
    }
}
