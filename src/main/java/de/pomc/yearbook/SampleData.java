package de.pomc.yearbook;

import de.pomc.yearbook.user.User;
import de.pomc.yearbook.web.book.Book;
import de.pomc.yearbook.web.book.Participation;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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
        new User((long) 0, "Frodo Baggins", "frodo.baggins@shire.com", "1234", "USER"),
        new User((long) 1, "Samwise Gamgee", "sam.gamgee@shire.com", "1234", "USER"),
        new User((long) 2, "Gandalf the Gray", "gandalf.gray@hotmail.com", "1234", "USER"),
        new User((long) 3, "Legolas", "legolas@woodland.com", "1234", "USER"),
        new User((long) 4, "Gimli the Dwarf", "gimli.dwarf@blueMountain.com", "1234", "USER")
    );

    public static List<List<Participation>> participationsList = List.of(
        List.of(
            new Participation(users.get(0), false),
            new Participation(users.get(1), false),
            new Participation(users.get(2), true)
        ),
        List.of(
            new Participation(users.get(3), false),
            new Participation(users.get(1), false),
            new Participation(users.get(4), true)
        ),
        List.of(
            new Participation(users.get(4), false),
            new Participation(users.get(3), false),
            new Participation(users.get(1), true)
        ),
        List.of(
            new Participation(users.get(0), false),
            new Participation(users.get(4), false),
            new Participation(users.get(3), true)
        ),
        List.of(
            new Participation(users.get(2), false),
            new Participation(users.get(1), false),
            new Participation(users.get(0), true)
        )
    );

    @Getter
    @Setter
    public static List<Book> books = List.of(
        new Book((long) 0, "Blue Mountain State 2020", "some description", questionsList.get(0), participationsList.get(0)),
        new Book((long) 1, "HSBA BI '21", "some description", questionsList.get(1), participationsList.get(1)),
        new Book((long) 2, "Stanford Law '19", "some description", questionsList.get(2), participationsList.get(2)),
        new Book((long) 3, "MIT Robotics 2020", "some description", questionsList.get(3), participationsList.get(3)),
        new Book((long) 4, "NYU Gender Sciences 2019", "some description", questionsList.get(4), participationsList.get(4))
    );

}