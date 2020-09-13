package de.pomc.yearbook.book;

import static org.assertj.core.api.Assertions.assertThat;
import de.pomc.yearbook.participation.Participation;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void userShouldHaveParticipation() {
        // given
        book.getParticipations().add(new Participation(userTwo, true));

        // when
        boolean userTwoHasParticipation = book.userHasParticipation(userTwo);

        // then
        assertThat(userTwoHasParticipation).isTrue();
    }

    @Test
    void userShouldNotHaveParticipation() {
        // given -> setup

        // when
        boolean userOneHasParticipation = book.userHasParticipation(userOne);

        // then
        assertThat(userOneHasParticipation).isFalse();
    }

    @Test
    void currentUserShouldHaveParticipation() {
        // given
        book.getParticipations().add(new Participation(userOne, false));

        // when
        boolean currentUserIsParticipant = book.currentUserHasParticipation();

        // then
        assertThat(currentUserIsParticipant).isTrue();
    }

    @Test
    void currentUserShouldNotHaveParticipation() {
        // given -> setup

        // when
        boolean currentUserIsParticipant = book.currentUserHasParticipation();

        // then
        assertThat(currentUserIsParticipant).isFalse();
    }

    @Test
    void shouldBeOwner() {
        // given -> setup
        Participation participation = new Participation(userOne, true);
        book.getParticipations().add(participation);


        // when
        boolean isOwner = book.isOwner(participation);

        // then
        assertThat(isOwner).isTrue();
    }

    @Test
    void shouldNotBeOwner() {
        // given -> setup
        Participation participation = new Participation(userTwo, true);
        book.getParticipations().add(participation);


        // when
        boolean isOwner = book.isOwner(participation);

        // then
        assertThat(isOwner).isFalse();
    }

    @Test
    void currentUserShouldBeOwner() {
        // given -> setup

        // when
        boolean currentUserIsOwner = book.currentUserIsOwner();

        // then
        assertThat(currentUserIsOwner).isTrue();
    }

    @Test
    void currentUserShouldNotBeOwner() {
        // given
        book.setOwner(userTwo);

        // when
        boolean currentUserIsOwner = book.currentUserIsOwner();

        // then
        assertThat(currentUserIsOwner).isFalse();
    }

    @Test
    void currentUserShouldBeAdmin() {
        // given
        book.getParticipations().add(new Participation(userOne, true));

        // when
        boolean currentUserIsAdmin = book.currentUserIsAdmin();

        // then
        assertThat(currentUserIsAdmin).isTrue();
    }

    @Test
    void currentUserShouldNotBeAdmin() {
        // given
        book.getParticipations().add(new Participation(userOne, false));

        // when
        boolean currentUserIsAdmin = book.currentUserIsAdmin();

        // then
        assertThat(currentUserIsAdmin).isFalse();
    }

    @Test
    void currentUserShouldBeAbleToDeleteSelf() {
        // given
        book.getParticipations().add(new Participation(userOne, true));

        // when
        boolean currentUserCanDelete = book.currentUserCanDelete(0);

        // then
        assertThat(currentUserCanDelete).isTrue();
    }

    @Test
    void currentUserShouldBeAbleToDeleteAdmin() {
        // given
        book.getParticipations().add(new Participation(userTwo, true));

        // when
        boolean currentUserCanDelete = book.currentUserCanDelete(0);

        // then
        assertThat(currentUserCanDelete).isTrue();
    }

    @Test
    void currentUserShouldBeAbleToDeleteUser() {
        // given
        book.getParticipations().add(new Participation(userTwo, true));

        // when
        boolean currentUserCanDelete = book.currentUserCanDelete(0);

        // then
        assertThat(currentUserCanDelete).isTrue();
    }

    @Test
    void currentUserShouldNotBeAbleToDeleteNonExistant() {
        // given -> setup

        // when
        boolean currentUserCanDelete = book.currentUserCanDelete(1000);

        // then
        assertThat(currentUserCanDelete).isFalse();
    }
}
