package de.pomc.yearbook.book;


import de.pomc.yearbook.SampleData;
import de.pomc.yearbook.participation.Participation;
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

    public List<Book> getBooksOfCurrentUser() {
        return SampleData.getBooks().stream()
                .filter(Book::currentUserIsOwner)
                .collect(Collectors.toList());
    }

    public Book getBookWithID(Long id) {
        return SampleData.getBooks()
                .stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Book> getPublishedBooks() {
        return SampleData.getBooks().stream()
                .filter(Book::isPublished)
                .collect(Collectors.toList());
    }

    public List<Participation> getParticipationsOfCurrentUser() {
        List<Participation> participations = new ArrayList<>();

        SampleData.getBooks().forEach(book -> {
            book.getParticipations().forEach(participation -> {
               if (participation.currentUserIsOwner()) {
                   participations.add(participation);
               }
            });
        });

        return participations;
    }

    public Book save(Book book) {
        List<Book> books = new ArrayList<>(SampleData.getBooks());
        books.add(book);

        SampleData.setBooks(books);

        return book;
    }
}
