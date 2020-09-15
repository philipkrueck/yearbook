package de.pomc.yearbook.book;

import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private final User userOne = new User("User", "One", "user.one@mail.com", "1234");
    private final User userTwo = new User("User", "Two", "user.two@mail.com", "1234");

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        entityManager.persist(userOne);
        entityManager.persist(userTwo);
    }

    @Test
    void itShouldFindBooksOfCurrentUser() {
        // given
        Book bookOne = buildBookWithOwner(userOne, false);
        Book bookTwo = buildBookWithOwner(userOne, false);
        Book bookThree = buildBookWithOwner(userOne, false);
        Book bookFour = buildBookWithOwner(userTwo, false); // this book should not be found

        // inject User into authentication object
        UserDetails userDetails = new UserAdapter(userOne);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // when
        List<Book> foundBooksOfCurrentUser = bookService.getBooksOfCurrentUser();

        // then
        assertThat(foundBooksOfCurrentUser).containsExactlyInAnyOrder(bookOne, bookTwo, bookThree);
    }

    @Test
    void itShouldFindPublishedBooks() {
        // given
        Book bookOne = buildBookWithOwner(userOne, false); // this book shouldn't be found
        Book bookTwo = buildBookWithOwner(userOne, true);
        Book bookThree = buildBookWithOwner(userTwo, true);

        // when
        List<Book> foundPublishedBooks = bookService.getPublishedBooks();

        // then
        assertThat(foundPublishedBooks).containsExactlyInAnyOrder(bookTwo, bookThree);
    }

    private Book buildBookWithOwner(User owner, boolean published) {
        Book book = new Book("Title", "Description", owner, published);
        return bookService.save(book);
    }
}
