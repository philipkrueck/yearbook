package de.pomc.yearbook.book;


import de.pomc.yearbook.SampleData;
import de.pomc.yearbook.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
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
                .filter(Book::isOwnedByCurrentUser)
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
}
