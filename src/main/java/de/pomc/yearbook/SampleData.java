package de.pomc.yearbook;

import de.pomc.yearbook.participation.Comment;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.participation.Participation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// This acts as our dummy data base before we are adding services and repositories
public class SampleData {

    public static List<List<String>> questionsList = List.of(
            List.of("What is your favorite movie?", "What is your favorite book?", "What is your favorite ice cream flavor"),
            List.of("Is cereal soup? Why or why not?", "What’s the best Wi-Fi name you’ve seen?", "How do you feel about putting pineapple on pizza?"),
            List.of("Toilet paper, over or under?", "What would be the absolute worst name you could give your child?", "What sport would be the funniest to add a mandatory amount of alcohol to?"),
            List.of("What Part Of Your Body Could Use A Little Lotion?", "What Part Of The Human Face Is Your Favorite?"),
            List.of("If You Could Wedgie Any Historical Figure, Who Would You Pick?", " Would You Rather Be Able To Breathe Underwater Or Have The Agility Of A Cat?")
    );

    @Getter
    public static List<User> users = List.of(
        new User((long) 0, "Frodo", "Baggins", "frodo.baggins@shire.com", "1234", "USER"),
        new User((long) 1, "Samwise", "Gamgee", "sam.gamgee@shire.com", "1234", "USER"),
        new User((long) 2, "Gandalf",  "the Gray", "gandalf.gray@hotmail.com", "1234", "USER"),
        new User((long) 3, "Legolas", "Son of Thranduil", "legolas@woodland.com", "1234", "USER"),
        new User((long) 4, "Gimli", "Son of Gloin", "gimli.dwarf@blueMountain.com", "1234", "USER")
    );
    
    @Getter
    private static List<String> defaultAnswers = List.of(
        "Not sure", "Great Question", "Say that again?"
    );

    @Getter
    @Setter
    private static List<Comment> comments = List.of(
        new Comment("Great Guy", users.get(0)),
        new Comment("Has a great voice", users.get(1)),
        new Comment("Is way too slow", users.get(2)),
        new Comment("small dude", users.get(3))
    );

    @Getter
    public static List<Participation> participations = List.of(
            new Participation((long) 0, users.get(0), false, defaultAnswers, comments),
            new Participation((long) 1, users.get(1), false, defaultAnswers, comments),
            new Participation((long) 2, users.get(2), true, defaultAnswers, comments),
            new Participation((long) 3, users.get(3), false, defaultAnswers, comments),
            new Participation((long) 4, users.get(1), false, defaultAnswers, comments),
            new Participation((long) 5, users.get(4), true, defaultAnswers, comments),
            new Participation((long) 6, users.get(4), false, defaultAnswers, comments),
            new Participation((long) 7, users.get(3), false, defaultAnswers, comments),
            new Participation((long) 8, users.get(1), true, defaultAnswers, comments),
            new Participation((long) 9, users.get(0), true, defaultAnswers, comments),
            new Participation((long) 10, users.get(4), false, defaultAnswers, comments),
            new Participation((long) 11, users.get(3), true, defaultAnswers, comments),
            new Participation((long) 12, users.get(2), false, defaultAnswers, comments),
            new Participation((long) 13, users.get(1), false, defaultAnswers, comments),
            new Participation((long) 14, users.get(0), true, defaultAnswers, comments)
    );

    public static List<List<Participation>> participationsList = List.of(
        List.of(
            participations.get(0),
            participations.get(1),
            participations.get(2)
        ),
        List.of(
            participations.get(3),
            participations.get(4),
            participations.get(5)
        ),
        List.of(
            participations.get(6),
            participations.get(7),
            participations.get(8)
        ),
        List.of(
            participations.get(9),
            participations.get(10),
            participations.get(11)
        ),
        List.of(
            participations.get(12),
            participations.get(13),
            participations.get(14)
        )
    );

    @Getter
    @Setter
    public static List<Book> books = List.of(
        new Book((long) 0, "Blue Mountain State 2020", "some description", users.get(0), questionsList.get(0), participationsList.get(0), true),
        new Book((long) 1, "HSBA BI '21", "some description", users.get(1), questionsList.get(1), participationsList.get(1), true),
        new Book((long) 2, "Stanford Law '19", "some description", users.get(1), questionsList.get(2), participationsList.get(2), true),
        new Book((long) 3, "MIT Robotics 2020", "some description", users.get(1), questionsList.get(3), participationsList.get(3), true),
        new Book((long) 4, "NYU Gender Sciences 2019", "some description", users.get(1), questionsList.get(4), participationsList.get(4), true)
    );

}
