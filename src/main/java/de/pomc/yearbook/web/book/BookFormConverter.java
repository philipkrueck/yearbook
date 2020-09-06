package de.pomc.yearbook.web.book;

import de.pomc.yearbook.book.Book;

public abstract class BookFormConverter {

    public static BookForm bookForm(Book book) {
        return new BookForm(book.getName(), book.getDescription(), book.isPublished());
    }

    public static Book update(Book book, BookForm bookForm) {
        book.setName(bookForm.getName());
        book.setDescription(bookForm.getDescription());
        book.setPublished(bookForm.isPublished());
        return book;
    }
}
