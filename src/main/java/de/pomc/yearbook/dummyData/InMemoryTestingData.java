package de.pomc.yearbook.dummyData;

// NOTE: This class is only used to create dummy data for debug builds.
// Once we go into production, we will unlink this code from the compilation process.

import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.participation.ParticipationService;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InMemoryTestingData {

    private final UserService userService;
    private final BookService bookService;
    private final ParticipationService participationService;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        // init users
        if (!userService.findAll().isEmpty()) {
            // prevent duplicate initialization if DB is not empty
            return;
        }
        User sam = new User((long) 1, "Samwise", "Gamgee", "sam.gamgee@shire.com", passwordEncoder.encode("1234"), "USER");
        User gandalf = new User((long) 2, "Gandalf",  "the Gray", "gandalf.gray@hotmail.com", passwordEncoder.encode("1234"), "USER");
        User legolas = new User((long) 3, "Legolas", "Son of Thranduil", "legolas@woodland.com", passwordEncoder.encode("1234"), "USER");
        User gimli = new User((long) 4, "Gimli", "Son of Gloin", "gimli.dwarf@blueMountain.com", passwordEncoder.encode("1234"), "USER");
        User frodo = new User((long) 5, "Frodo", "Baggins", "frodo.baggins@shire.com", passwordEncoder.encode("1234"), "USER");

        List.of(sam, gandalf, legolas, gimli, frodo)
                .forEach(userService::save);

        // init books
        if (!bookService.findAll().isEmpty()) {
            // prevent duplicate initialization if DB is not empty
            return;
        }
        List<String> questions = List.of("question one", "question two", "question three");

        Book bookOne = new Book("Blue Mountain State 2020", "description", sam);
        Book bookTwo = new Book("HSBA BI '21", "description", gandalf);
        Book bookThree = new Book("Stanford Law '19", "description", legolas);
        Book bookFour = new Book("MIT Robotics 2020", "description", gimli);
        Book bookFive = new Book("NYU Gender Sciences 2019", "description", frodo);

        List.of(bookOne, bookTwo, bookThree, bookFour, bookFive)
                .forEach(bookService::save);

        // init participations

    }

}
