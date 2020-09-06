package de.pomc.yearbook.web.book;

import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserService;

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

    public static Book book(BookForm bookForm, User user) {
        Book book = new Book(bookForm.getName(), bookForm.getDescription(), user);
        book.setPublished(bookForm.isPublished());
        return book;
    }
}
