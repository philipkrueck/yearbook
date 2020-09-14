package de.pomc.yearbook.web.book;

public class CreateBookForm extends BookForm {

    private byte[] image;

    public CreateBookForm(String name, String description, boolean published, byte[] image) {
        super(name, description, published);
        this.image = image;
    }
}
