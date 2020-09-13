package de.pomc.yearbook.book;

import static org.assertj.core.api.Assertions.assertThat;
import de.pomc.yearbook.participation.Participation;
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

import java.util.ArrayList;
import java.util.List;

public class BookTest {

    User userOne;
    User userTwo;
    Book book;
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

        book = new Book("sample", "...", userOne, false);
    }

    @Test
    void itShouldValidatedUserHasParticipation() {
        // given
        book.getParticipations().add(new Participation(userTwo, true));

        // when
        boolean userTwoHasParticipation = book.userHasParticipation(userTwo);

        // then
        assertThat(userTwoHasParticipation).isTrue();
    }

    @Test
    void itShouldNotValidatedUserHasParticipation() {
        // given -> setup

        // when
        boolean userOneHasParticipation = book.userHasParticipation(userOne);

        // then
        assertThat(userOneHasParticipation).isFalse();
    }

    @Test
    void itShouldValidateCurrentUserHasParticipation() {
        // given
        book.getParticipations().add(new Participation(userOne, false));

        // when
        boolean currentUserIsParticipant = book.currentUserHasParticipation();

        // then
        assertThat(currentUserIsParticipant).isTrue();
    }

    @Test
    void itShouldNotValidateCurrentUserHasParticipation() {
        // given -> setup

        // when
        boolean currentUserIsParticipant = book.currentUserHasParticipation();

        // then
        assertThat(currentUserIsParticipant).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "0, true",
            "1, false"
    })
    void itShouldValidateIsOwner(int userIndex, boolean expected) {
        // given -> setup
        Participation participation = new Participation(users.get(userIndex), true);
        book.getParticipations().add(participation);


        // when
        boolean isOwner = book.isOwner(participation);

        // then
        assertThat(isOwner).isEqualTo(expected);
    }

    @Test
    void itShouldValidateCurrentUserIsOwner() {
        // given -> setup

        // when
        boolean currentUserIsOwner = book.currentUserIsOwner();

        // then
        assertThat(currentUserIsOwner).isTrue();
    }

    @Test
    void itShouldNotValidateCurrentUserIsOwner() {
        // given
        book.setOwner(userTwo);

        // when
        boolean currentUserIsOwner = book.currentUserIsOwner();

        // then
        assertThat(currentUserIsOwner).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "true, true",
            "false, false"
    })
    void itShouldValidateCurrentUserIsAdmin(boolean isAdmin, boolean expected) {
        // given
        book.getParticipations().add(new Participation(userOne, expected));

        // when
        boolean currentUserIsAdmin = book.currentUserIsAdmin();

        // then
        assertThat(currentUserIsAdmin).isEqualTo(isAdmin);
    }

    @ParameterizedTest
    @CsvSource({
            "0, true, 0, true",
            "1, true, 0, true",
            "1, false, 0, true",
            "1, false, 1000, false"

    })
    void itShoulcValidateCurrentUserCanDelete(int userIndex, boolean isAdmin, int participationId, boolean expected) {
        // given
        book.getParticipations().add(new Participation(users.get(userIndex), isAdmin));

        // when
        boolean currentUserCanDelete = book.currentUserCanDelete(participationId);

        // then
        assertThat(currentUserCanDelete).isEqualTo(expected);
    }
}
