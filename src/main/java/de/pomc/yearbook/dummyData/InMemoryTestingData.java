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
import java.util.Random;

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
    private User arwin;


    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        // init users
        if (!userService.findAll().isEmpty()) {
            // prevent duplicate initialization if DB is not empty
            return;
        }

        sam = new User((long) 1, "Samwise", "Gamgee", "sam.gamgee@shire.com", passwordEncoder.encode("Rosie13#loveyou"), "USER");
        sam.setTwitterHandle("@SamTheWiseGamgee");
        sam.setLocation("The Shire");
        sam.setWebsite("http://shire.com");
        gandalf = new User((long) 2, "Gandalf", "the Gray", "gandalf.gray@notyetwhite.com", passwordEncoder.encode("ITrustHobbits<3"), "USER");
        gandalf.setTwitterHandle("@WashedMyCloak");
        gandalf.setLocation("between realms");
        gandalf.setWebsite("http://angels.com/gandalf");
        legolas = new User((long) 3, "Legolas", "Son of Thranduil", "legolas@woodland.com", passwordEncoder.encode("TakingTheH0bi!!sToIsengard"), "USER");
        legolas.setTwitterHandle("@Bow-ya");
        legolas.setLocation("former Isengard");
        legolas.setWebsite("http://greenpeace.com");
        gimli = new User((long) 4, "Gimli", "Son of Gloin", "gimli.dwarf@blueMountain.com", passwordEncoder.encode("GaladriellF0rL!fe"), "USER");
        gimli.setTwitterHandle("@ImABeardo");
        gimli.setLocation("Moria");
        gimli.setWebsite("http://smallbutoho.com");
        frodo = new User((long) 5, "Frodo", "Baggins", "frodo.baggins@shire.com", passwordEncoder.encode("SecretSamLover69#"), "USER");
        frodo.setTwitterHandle("TheOne");
        frodo.setLocation("on vacation ... somewhere");
        frodo.setWebsite("http://shire.com");
        arwin = new User((long) 6, "Arwin", "Undomiell", "arwin@riverdale.com", passwordEncoder.encode("_FuckIm0rtality_"), "USER");
        arwin.setTwitterHandle("@FirstLadyOfGondor");
        arwin.setLocation("Gondor");
        arwin.setWebsite("http://firstlady.com");

        frodo.setRole("ADMIN");
        gandalf.setRole("ADMIN");

        byte[] frodoImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/user/frodo.jpg");
        byte[] samImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/user/sam.jpg");
        byte[] gandalfImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/user/gandalf.jpg");
        byte[] legolasImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/user/legolas.jpg");
        byte[] gimliImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/user/gimli.jpg");
        byte[] arwinImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/user/arwin.jpg");

        List<User> users = List.of(sam, gandalf, legolas, gimli, frodo, arwin);

        sam.setImage((samImage));
        gandalf.setImage((gandalfImage));
        legolas.setImage((legolasImage));
        gimli.setImage((gimliImage));
        frodo.setImage((frodoImage));
        arwin.setImage((arwinImage));

        users.forEach(userService::save);

        // init books
        if (!bookService.findAll().isEmpty()) {
            // prevent duplicate initialization if DB is not empty
            return;
        }
        Book bookOne = new Book("Shire Memories", "what we love about our teletabiland", sam, true);
        Book bookTwo = new Book("Moria 3019", "worst seafood ever...", gimli, true);
        Book bookThree = new Book("Weekend in Rivendell", "Veggies make you strong", arwin, true);
        Book bookFour = new Book("Camping in Lothlorien", "best elvenbread ever", legolas, true);

        List<Book> books = List.of(bookOne, bookTwo, bookThree, bookFour);

        byte[] shireImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/book/shire.jpg");
        byte[] moriaImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/book/moria.jpg");
        byte[] rivendellImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/book/riverdale.jpg");
        byte[] lothlorienImage = imageLoader.loadImageFromPath("src/main/resources/testingImages/book/lothlorien.jpg");

        bookOne.setImage(shireImage);
        bookTwo.setImage(moriaImage);
        bookThree.setImage(rivendellImage);
        bookFour.setImage(lothlorienImage);

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
                new Participation(gandalf, true),
                new Participation(legolas, false)
        );

        List<Participation> bookThreeParticipations = List.of(
                new Participation(sam, false),
                new Participation(legolas, true),
                new Participation(arwin, true)
        );

        List<Participation> bookFourParticipations = List.of(
                new Participation(sam, false),
                new Participation(gimli, true),
                new Participation(legolas, false)
        );

        List<List<Question>> questionsList = List.of(
                List.of(new Question("What was the best birthday party?"), new Question("Where is your favorite pipe smoking spot?"), new Question("Which is your favorite breakfast?")),
                List.of(new Question("How surprised were you when I opened the gates?"), new Question("Who of you wants to go back with me and search for treasure?"), new Question("Who shit their pants when fighting the Balrock?")),
                List.of(new Question("Which vegetable did you like best?"), new Question("What happend to Boromir?"), new Question("Who broke Aragon's sword, again?")),
                List.of(new Question("Who baked the tastiest Lembert bread?"), new Question("Who took the elven cloak?"))
        );

        List<List<Participation>> bookParticipations = List.of(bookOneParticipations, bookTwoParticipations, bookThreeParticipations, bookFourParticipations);

        for (int i = 0; i <= 3; i++) {
            Book book = books.get(i);
            List<Participation> participations = bookParticipations.get(i);

            addParticipations(participations, book);
            addQuestions(book, questionsList.get(i));

            bookService.save(book);
        }

        // Sam book one
        var participationBookOneSam = bookOneParticipations.get(0);
        List<Answer> bookOneSamAnswers = List.of(
                new Answer("Bilbos 100th birthday, for sure!"),
                new Answer("At home with all windows closed"),
                new Answer("Second!")
        );
        participationService.setAnswers(participationBookOneSam, bookOneSamAnswers);
        // Gandalf book one
        var participationBookOneGandalf = bookOneParticipations.get(1);
        List<Answer> bookOneGandalfAnswers = List.of(
                new Answer("Frodos 1st birthday was very special"),
                new Answer("On my carriage driving through the shire"),
                new Answer("I don't have to eat lol")
        );
        participationService.setAnswers(participationBookOneGandalf, bookOneGandalfAnswers);
        // Frodo book one
        var participationBookOneFrodo = bookOneParticipations.get(2);
        List<Answer> bookOneFrodoAnswers = List.of(
                new Answer("I think im going with Bilbos 100th birthday! I won't forget this one.."),
                new Answer("On my carriage driving through the shire"),
                new Answer("The first breakfast sets the mood for the day. I love it.")
        );
        participationService.setAnswers(participationBookOneFrodo, bookOneFrodoAnswers);
        bookService.save(bookOne);

        // Gandalf book two
        var participationBookTwoGandalf = bookTwoParticipations.get(0);
        List<Answer> bookTwoGandalfAnswers = List.of(
                new Answer("I know this mountain like the back of my hand!"),
                new Answer("Im good, thanks.."),
                new Answer("Hahaha, can eagles fly? Of course!")
        );
        participationService.setAnswers(participationBookTwoGandalf, bookTwoGandalfAnswers);
        // Legolas book two
        var participationBookTwoLegolas = bookTwoParticipations.get(1);
        List<Answer> bookTwoLegolasAnswers = List.of(
                new Answer("My bow was ready!"),
                new Answer("Maybe one day... If there are fewer corpses lying around, I'd love to!"),
                new Answer("I don't need to be faster than the Balrog, just faster than the slowest of you!")
        );
        participationService.setAnswers(participationBookTwoLegolas, bookTwoLegolasAnswers);
        bookService.save(bookTwo);

        // Sam book three
        var participationBookThreeSam = bookThreeParticipations.get(0);
        List<Answer> bookThreeSamAnswers = List.of(
                new Answer("Po-ta-toes."),
                new Answer("He died with honor! "),
                new Answer("He himself, he wanted to train with us!")
        );
        participationService.setAnswers(participationBookThreeSam, bookThreeSamAnswers);
        // Legolas book three
        var participationBookThreeLegolas = bookThreeParticipations.get(1);
        List<Answer> bookThreeLegolasAnswers = List.of(
                new Answer("Eggplant."),
                new Answer("He was seduced by the ring."),
                new Answer("Ask the hobbits!")
        );
        participationService.setAnswers(participationBookThreeLegolas, bookThreeLegolasAnswers);
        // Arwin book three
        var participationBookThreeArwin = bookThreeParticipations.get(2);
        List<Answer> bookThreeArwinAnswers = List.of(
                new Answer("All fruits of nature are worth to be enjoyed."),
                new Answer("I have never asked myself this question.."),
                new Answer("What?! I just repaired it..")
        );
        participationService.setAnswers(participationBookThreeArwin, bookThreeArwinAnswers);
        bookService.save(bookThree);

        // Sam book four
        var participationBookFourSam = bookFourParticipations.get(0);
        List<Answer> bookFourSamAnswers = List.of(
                new Answer("So Gollum likes to do trouble with it.."),
                new Answer("Frodo has it! Looks good on him")
        );
        participationService.setAnswers(participationBookFourSam, bookFourSamAnswers);
        // Gimli book four
        var participationBookFourGimli = bookFourParticipations.get(1);
        List<Answer> bookFourGimliAnswers = List.of(
                new Answer("I definitely do not... I need deer meat."),
                new Answer("The what?")
        );
        participationService.setAnswers(participationBookFourGimli, bookFourGimliAnswers);
        // Legolas book four
        var participationBookFourLegolas = bookFourParticipations.get(2);
        List<Answer> bookFourLegolasAnswers = List.of(
                new Answer("Elrond is definitely a master baker"),
                new Answer("Ah yes, the holy cloak of my people! I hope it is well hidden.")
        );
        participationService.setAnswers(participationBookFourLegolas, bookFourLegolasAnswers);
        bookService.save(bookFour);

    }

    private void addParticipations(List<Participation> participations, Book book) {
        for (Participation participation: participations) {
            addComments(participation, participations);
            bookService.addParticipation(book, participation);
        }
    }

    private void addComments(Participation participation, List<Participation> commenters) {

        ArrayList<Comment> comments = new ArrayList<>();

        for (Participation commenter: commenters) {
            if (participation.getParticipant() != commenter.getParticipant()) {
                    comments.add(new Comment(generateComment(), commenter.getParticipant()));
            }
        }
        comments.forEach(comment -> participationService.addComment(comment, participation));
    }

    private String generateComment() {
        List<String> friendlyAdjectives = List.of(
                new String("great"),
                new String("noice"),
                new String("best"),
                new String("super")
        );

        List<String> unfriendlyAdjectives = List.of(
                new String("terrible"),
                new String("weird"),
                new String("unfriendly"),
                new String("worst")
        );

        List<String> nouns = List.of(
                new String("person"),
                new String("cook"),
                new String("hiker"),
                new String("fighter")
        );

        Random rand = new Random();
        boolean randomFriendliness = rand.nextBoolean();
        int randomNoun = rand.nextInt(nouns.size());

        if (randomFriendliness) {
            int randomAdjective = rand.nextInt(friendlyAdjectives.size());
            return friendlyAdjectives.get(randomAdjective) + " " + nouns.get(randomNoun);
        } else {
            int randomAdjective = rand.nextInt(unfriendlyAdjectives.size());
            return unfriendlyAdjectives.get(randomAdjective) + " " + nouns.get(randomNoun);
        }
    }

    private void addQuestions(Book book, List<Question> questions) {
        bookService.setQuestions(book, questions);
    }

}
