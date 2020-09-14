package de.pomc.yearbook.web.book;

import de.pomc.yearbook.book.Book;

public abstract class BookImageFormConverter {

    public static BookImageForm bookImageForm(Book book) {

        byte[] image = book.getImage();

        return new BookImageForm(image);
    }

    public static Book update(Book book, BookImageForm bookImageForm) {

        book.setImage(bookImageForm.getImage());

        return book;
    }
}
