package de.pomc.yearbook.web.book;

import lombok.Getter;

public abstract class BookViewModelConverter {

    public static BookViewModel bookViewModel(Book book) {
        return new BookViewModel(book.getId(), book.getName(), book.getDescription(), book.isOwnedByCurrentUser());
    }
}
