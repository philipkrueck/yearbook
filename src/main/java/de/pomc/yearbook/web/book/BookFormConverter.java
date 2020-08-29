package de.pomc.yearbook.web.book;

import de.pomc.yearbook.book.Book;

public abstract class BookFormConverter {

    public static BookForm bookForm(Book book) {
        return new BookForm(book.getName(), book.getDescription());
    }

    public static Book update(Book book, BookForm bookForm) {
        book.setName(bookForm.getName());
        book.setDescription(bookForm.getDescription());
        return book;
    }
}
