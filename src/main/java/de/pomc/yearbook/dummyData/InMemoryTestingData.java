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
    private User gollum;
    private User arwin;


    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        // init users
        if (!userService.findAll().isEmpty()) {
            // prevent duplicate initialization if DB is not empty
            return;
        }

        sam = new User((long) 1, "Samwise", "Gamgee", "sam.gamgee@shire.com", passwordEncoder.encode("Rosie13#loveyou"), "USER");
        gandalf = new User((long) 2, "Gandalf", "the Gray", "gandalf.gray@notyetwhite.com", passwordEncoder.encode("ITrustHobbits<3"), "USER");
        legolas = new User((long) 3, "Legolas", "Son of Thranduil", "legolas@woodland.com", passwordEncoder.encode("TakingTheH0bi!!sToIsengarg"), "USER");
        gimli = new User((long) 4, "Gimli", "Son of Gloin", "gimli.dwarf@blueMountain.com", passwordEncoder.encode("GaladriellF0rL!fe"), "USER");
        frodo = new User((long) 5, "Frodo", "Baggins", "frodo.baggins@shire.com", passwordEncoder.encode("SecretSamLover69#"), "USER");
        gollum = new User((long) 6, "Smeagol", "Gollum", "smeagol@shire.com", passwordEncoder.encode("StabMeKinkra4Real"), "USER");
        arwin = new User((long) 7, "Arwin", "Undomiell", "arwin@riverdale.com", passwordEncoder.encode("_FuckIm0rtality_"), "USER");

        frodo.setRole("ADMIN");
        gandalf.setRole("ADMIN");

        byte[] frodoImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/user/frodo.jpg");
        byte[] samImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/user/sam.jpg");
        byte[] gandalfImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/user/gandalf.jpg");
        byte[] legolasImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/user/legolas.jpg");
        byte[] gimliImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/user/gimli.jpg");
        byte[] gollumImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/user/gollum.jpg");
        byte[] arwinImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/user/arwin.jpg");

        List<User> users = List.of(sam, gandalf, legolas, gimli, frodo, gollum, arwin);

        sam.setImage((samImage));
        gandalf.setImage((gandalfImage));
        legolas.setImage((legolasImage));
        gimli.setImage((gimliImage));
        frodo.setImage((frodoImage));
        gollum.setImage((gollumImage));
        arwin.setImage((arwinImage));

        users.forEach(userService::save);

        // init books
        if (!bookService.findAll().isEmpty()) {
            // prevent duplicate initialization if DB is not empty
            return;
        }
        List<String> questions = List.of("question one", "question two", "question three");

        Book bookOne = new Book("Shire Memories", "what we love about our teletabiland", sam, true);
        Book bookTwo = new Book("Moria 3019", "worst seafood ever...", gimli, true);
        Book bookThree = new Book("Weekend in Rivendell", "Veggies make you strong", arwin, true);
        Book bookFour = new Book("Camping in Lothlorien", "best elvenbread ever", legolas, true);
        Book bookFive = new Book("Hiking to Mount Doom", "dont loose your rings again!", frodo, false);
        bookOne.setPublished(true);

        List<Book> books = List.of(bookOne, bookTwo, bookThree, bookFour, bookFive);

        byte[] shireImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/book/shire.jpg");
        byte[] moriaImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/book/moria.jpg");
        byte[] rivendellImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/book/riverdale.jpg");
        byte[] lothlorienImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/book/lothlorien.jpg");
        byte[] mountDoomImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/book/mountDoom.jpg");

        bookOne.setImage(shireImage);
        bookTwo.setImage(moriaImage);
        bookThree.setImage(rivendellImage);
        bookFour.setImage(lothlorienImage);
        bookFive.setImage(mountDoomImage);

        // init participations
        if (!participationService.findAll().isEmpty()) {
            // prevent duplicate initialization if DB is not empty
            return;
        }

        List<Participation> bookOneParticipations = List.of(
                new Participation(sam, true),
                new Participation(gandalf, true),
                new Participation(frodo, true)
        );

        List<Participation> bookTwoParticipations = List.of(
                new Participation(sam, true),
                new Participation(gandalf, true),
                new Participation(legolas, false),
                new Participation(gimli, true),
                new Participation(frodo, true),
                new Participation(gollum, false)
        );

        List<Participation> bookThreeParticipations = List.of(
                new Participation(sam, false),
                new Participation(gandalf, true),
                new Participation(legolas, true),
                new Participation(gimli, false),
                new Participation(frodo, true),
                new Participation(arwin, true)
        );

        List<Participation> bookFourParticipations = List.of(
                new Participation(sam, false),
                new Participation(gandalf, true),
                new Participation(legolas, true),
                new Participation(gimli, true),
                new Participation(frodo, false)
        );

        List<Participation> bookFiveParticipations = List.of(
                new Participation(sam, false),
                new Participation(gollum, false),
                new Participation(frodo, true)
        );

        List<List<Question>> questionsList = List.of(
                List.of(new Question("What was the best birthday party?"), new Question("Where is your favorite pipe smoking spot?"), new Question("Which is your favorite breakfast?")),
                List.of(new Question("How surprised were you when I opened the gates?"), new Question("Who of you wants to go back with me and search for treasure?"), new Question("Who shit their pants when fighting the Balrock?")),
                List.of(new Question("Which vegetable did you like best?"), new Question("What happend to Boromir?"), new Question("Who broke Aragon's sword, again?")),
                List.of(new Question("Who baked the tastiest Lembert bread?"), new Question("Who took the elven cloak?")),
                List.of(new Question("Who developed arachnophobia after this trip?"), new Question("What is your opinion about potatoes?"))
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
