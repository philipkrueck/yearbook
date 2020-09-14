package de.pomc.yearbook.book;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.List;

@MockitoSettings
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private BookService bookService;

    User user;
    List<Book> givenBooks;

    @BeforeEach
    void setUp() {
        user = new User("first", "last", "first.last@mail.com", "1234");
        givenBooks = List.of(new Book("Title", "Description", user, false));
    }

    @Test
    void itShouldFindBooksOfCurrentUser() {
        // given
        given(userService.findCurrentUser()).willReturn(user);
        given(bookRepository.findBooksByOwner(user)).willReturn(givenBooks);

        // when
        List<Book> booksOfCurrentUser = bookService.getBooksOfCurrentUser();

        // then
        assertThat(booksOfCurrentUser).isEqualTo(givenBooks);
        verify(bookRepository, never()).findAll();
    }

    @Test
    void itShouldFindPublishedBooks() {
        // given
        given(bookRepository.findPublishedBooks()).willReturn(givenBooks);

        // when
        List<Book> publishedBooks = bookService.getPublishedBooks();

        // then
        assertThat(publishedBooks).isEqualTo(givenBooks);
        verify(bookRepository, never()).findAll();
    }

}
