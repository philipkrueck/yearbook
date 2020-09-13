package de.pomc.yearbook.participation;

import static org.assertj.core.api.Assertions.assertThat;

import de.pomc.yearbook.book.Book;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class ParticipationTest {

    User userOne;
    User userTwo;
    List<User> users;

    @BeforeEach
    void setUp() {
        userOne = new User("User", "One", "user.one@gmail.com", "1234");
        userTwo = new User("User", "Two", "user.two@gmail.com", "1234");
        users = List.of(userOne, userTwo);

        // inject User into authentication object
        UserDetails userDetails = new UserAdapter(userOne);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @ParameterizedTest
    @CsvSource({
            "0, true",
            "1, false"
    })
    void itShouldValidateCurrentUserIsParticipant(int userIndex, boolean expected) {
        // given
        Participation participation = new Participation(users.get(userIndex), false);

        // when
        boolean currentUserIsParticipant = participation.currentUserIsParticipant();

        // then
        assertThat(currentUserIsParticipant).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "0, true",
            "1, false"
    })
    void itShouldValidateCurrentUserCanComment(int userIndex, boolean expected) {
        // given
        Book book = new Book("Title", "description", userOne, false);
        Participation participation = new Participation(users.get(userIndex), false);
        participation.setBook(book);

        book.getParticipations().add(participation);

        // when
        boolean currentUserCanComment = participation.currentUserCanComment();

        // then
        assertThat(currentUserCanComment).isEqualTo(expected);
    }

    @Test
    void itShouldGetNonBlankAnswerIndices() {
        // given
        Participation participation = new Participation(userOne, false);

        participation.getAnswers().add(new Answer("answer"));
        participation.getAnswers().add(new Answer(""));
        participation.getAnswers().add(new Answer("answer"));
        participation.getAnswers().add(new Answer("answer"));

        // when
        List<Integer> nonBlankAnswerIndices = participation.getNonBlankAnswerIndices();

        // then
        assertThat(nonBlankAnswerIndices.size() == 3).isTrue();
        assertThat(nonBlankAnswerIndices.contains(0)).isTrue();
        assertThat(nonBlankAnswerIndices.contains(2)).isTrue();
        assertThat(nonBlankAnswerIndices.contains(3)).isTrue();
    }

    @Test
    void itShouldGetEmptyNonBlankAnswerIndices() {
        // given
        Participation participation = new Participation(userOne, false);

        // when
        List<Integer> nonBlankAnswerIndices = participation.getNonBlankAnswerIndices();

        // then
        assertThat(nonBlankAnswerIndices.isEmpty()).isTrue();
    }


}
