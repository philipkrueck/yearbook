package de.pomc.yearbook.book;


import de.pomc.yearbook.SampleData;
import de.pomc.yearbook.participation.Participation;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserService userService;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksOfCurrentUser() {
        User currentUser = userService.findCurrentUser();
        if (currentUser == null) {
            return new ArrayList<>();
        }

        return bookRepository.findBooksByOwner(currentUser);
    }

    public Book getBookWithID(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    // ToDo: add further calls to bookService
    public List<Book> getPublishedBooks() {
        return SampleData.getBooks().stream()
                .filter(Book::isPublished)
                .collect(Collectors.toList());
    }

    public List<Participation> getParticipationsOfCurrentUser() {
        List<Participation> participations = new ArrayList<>();

        return participations;
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }
}
