package de.pomc.yearbook.dummyData;

// NOTE: This class is only used to create dummy data for debug builds.
// Once we go into production, we will unlink this code from the compilation process.

import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.book.BookService;
import de.pomc.yearbook.book.Question;
import de.pomc.yearbook.participation.Answer;
import de.pomc.yearbook.participation.Comment;
import de.pomc.yearbook.participation.Participation;
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
    private final ImageLoader imageLoader = new ImageLoaderImpl();

    private User sam;
    private User gandalf;
    private User legolas;
    private User gimli;
    private User frodo;


    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        // init users
        if (!userService.findAll().isEmpty()) {
            // prevent duplicate initialization if DB is not empty
            return;
        }

        sam = new User((long) 1, "Samwise", "Gamgee", "sam.gamgee@shire.com", passwordEncoder.encode("1234"), "USER");
        gandalf = new User((long) 2, "Gandalf", "the Gray", "gandalf.gray@hotmail.com", passwordEncoder.encode("1234"), "USER");
        legolas = new User((long) 3, "Legolas", "Son of Thranduil", "legolas@woodland.com", passwordEncoder.encode("1234"), "USER");
        gimli = new User((long) 4, "Gimli", "Son of Gloin", "gimli.dwarf@blueMountain.com", passwordEncoder.encode("1234"), "USER");
        frodo = new User((long) 5, "Frodo", "Baggins", "frodo.baggins@shire.com", passwordEncoder.encode("1234"), "USER");
        frodo.setRole("ADMIN");
        gandalf.setRole("ADMIN");

        // TODO: refactor this
        byte[] bookImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/book/graph.jpg");
        byte[] userImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/user/frodo.jpg");


        List<User> users = List.of(sam, gandalf, legolas, gimli, frodo);

        users.forEach(user -> user.setImage(userImage));
        users.forEach(userService::save);


        // init books
        if (!bookService.findAll().isEmpty()) {
            // prevent duplicate initialization if DB is not empty
            return;
        }
        List<String> questions = List.of("question one", "question two", "question three");

        Book bookOne = new Book("Blue Mountain State 2020", "description", sam, true);
        Book bookTwo = new Book("HSBA BI '21", "description", gandalf, true);
        Book bookThree = new Book("Stanford Law '19", "description", legolas, true);
        Book bookFour = new Book("MIT Robotics 2020", "description", gimli, false);
        Book bookFive = new Book("NYU Gender Sciences 2019", "description", frodo, false);
        bookOne.setPublished(true);

        List<Book> books = List.of(bookOne, bookTwo, bookThree, bookFour, bookFive);
        books.forEach(book -> book.setImage(bookImage));

        // init participations
        if (!participationService.findAll().isEmpty()) {
            // prevent duplicate initialization if DB is not empty
            return;
        }

        List<Participation> bookOneParticipations = List.of(
                new Participation(sam, false),
                new Participation(gandalf, false),
                new Participation(legolas, false),
                new Participation(gimli, false),
                new Participation(frodo, true)
        );

        List<Participation> bookTwoParticipations = List.of(
                new Participation(sam, true),
                new Participation(gandalf, true),
                new Participation(legolas, false),
                new Participation(gimli, false),
                new Participation(frodo, true)
        );

        List<Participation> bookThreeParticipations = List.of(
                new Participation(sam, false),
                new Participation(gandalf, true),
                new Participation(legolas, true),
                new Participation(gimli, false),
                new Participation(frodo, true)
        );

        List<Participation> bookFourParticipations = List.of(
                new Participation(sam, false),
                new Participation(gandalf, false),
                new Participation(legolas, true),
                new Participation(gimli, true),
                new Participation(frodo, false)
        );

        List<Participation> bookFiveParticipations = List.of(
                new Participation(sam, false),
                new Participation(gandalf, false),
                new Participation(legolas, false),
                new Participation(gimli, true),
                new Participation(frodo, true)
        );

        List<List<Question>> questionsList = List.of(
                List.of(new Question("What is your favorite movie?"), new Question("What is your favorite book?"), new Question("What is your favorite ice cream flavor")),
                List.of(new Question("Is cereal soup? Why or why not?"), new Question("What’s the best Wi-Fi name you’ve seen?"), new Question("How do you feel about putting pineapple on pizza?")),
                List.of(new Question("Toilet paper, over or under?"), new Question("What would be the absolute worst name you could give your child?"), new Question("What sport would be the funniest to add a mandatory amount of alcohol to?")),
                List.of(new Question("What Part Of Your Body Could Use A Little Lotion?"), new Question("What Part Of The Human Face Is Your Favorite?")),
                List.of(new Question("If You Could Wedgie Any Historical Figure, Who Would You Pick?"), new Question("Would You Rather Be Able To Breathe Underwater Or Have The Agility Of A Cat?"))
        );

        List<List<Participation>> bookParticipations = List.of(bookOneParticipations, bookTwoParticipations, bookThreeParticipations, bookFourParticipations, bookFiveParticipations);

        for (int i = 0; i <= 4; i++) {
            Book book = books.get(i);
            List<Participation> participations = bookParticipations.get(i);

            addParticipations(participations, book);
            addQuestions(book, questionsList.get(i));

            participations.forEach(this::addAnswers);

            bookService.save(book);
        }


    }

    private void addParticipations(List<Participation> participations, Book book) {
        for (Participation participation: participations) {
            addSomeComments(participation);
            bookService.addParticipation(book, participation);
        }
    }

    // ToDo: Maybe we can generate some comments here instead of using static comments
    private void addSomeComments(Participation participation) {
        List<Comment> comments = List.of(
                new Comment("Great guy", sam),
                new Comment("brilliant person", gandalf)
        );

        comments.forEach(comment -> participationService.addComment(comment, participation));
    }

    private void addQuestions(Book book, List<Question> questions) {
        bookService.setQuestions(book, questions);
    }

    private void addAnswers(Participation participation) {
        // add answers
        List<Answer> answers = List.of(
            new Answer("answer 1"), new Answer("answer 2")
        );
        participationService.setAnswers(participation, answers);
    }

}
