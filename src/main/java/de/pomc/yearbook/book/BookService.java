package de.pomc.yearbook.book;


import de.pomc.yearbook.participation.Answer;
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

    public void addParticipation(Book book, Participation participation) {
        participation.setBook(book);
        book.getParticipations().add(participation);
    }

    public void addQuestion(Book book, Question question) {
        question.setBook(book);
        book.getQuestions().add(question);
    }

    public void setQuestions(Book book, List<Question> questions) {
        questions.forEach(question -> question.setBook(book));
        book.setQuestions(questions);
    }

    public Book getBookWithID(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> getPublishedBooks() {
        return bookRepository.findPublishedBooks();
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }
}
