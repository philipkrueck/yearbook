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
    User userThree;
    List<User> users;

    @BeforeEach
    void setUp() {
        userOne = new User("User", "One", "user.one@gmail.com", "1234");
        userTwo = new User("User", "Two", "user.two@gmail.com", "1234");
        userThree = new User("User", "Three", "user.three@gmail.com", "1234");
        users = List.of(userOne, userTwo, userThree);

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
            "0",
            "1"
    })
    void itShouldValidateCurrentUserCantComment(int userIndex) {
        // given
        Book book = new Book("Title", "description", userOne, false);
        Participation participation = new Participation(users.get(userIndex), false);
        participation.setBook(book);

        book.getParticipations().add(participation);

        // when
        boolean currentUserCanComment = participation.currentUserCanComment();

        // then
        assertThat(currentUserCanComment).isFalse();
    }

    @Test
    void itShouldValidateCurrentUserComment() {
        // given
        Book book = new Book("Title", "description", userOne, false);
        Participation participationOne = new Participation(userOne, false);
        Participation participationTwo = new Participation(userTwo, false);

        participationOne.setBook(book);
        participationTwo.setBook(book);
        book.getParticipations().add(participationOne);
        book.getParticipations().add(participationTwo);

        // when
        boolean currentUserCanComment = participationTwo.currentUserCanComment();

        // then
        assertThat(currentUserCanComment).isTrue();
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

    @ParameterizedTest
    @CsvSource({
            "0, 0, false, Owner",
            "1, 0, true, Admin",
            "1, 0, false, Participant",
    })
    void itShouldGetStatusString(int bookOwnerUserIndex, int participationUserIndex, boolean isAdmin, String expected) {
        // given
        Book book = new Book("Title", "Description", users.get(bookOwnerUserIndex), false);
        Participation participation = new Participation(users.get(participationUserIndex), isAdmin);
        book.getParticipations().add(participation);
        participation.setBook(book);

        // when
        String statusString = participation.getStatusString();

        // then
        assertThat(statusString).isEqualTo(expected);
    }

    // NOTE: We have three roles per participation (owner, admin, participant).
    // Thus we have 3x3 combinations of the current users' and the participants' participation role.
    // The following tests will test all combinations.

    @ParameterizedTest
    @CsvSource({
            "0, false, 0, false, false", // currentUser: owner, viewUser: owner
            "0, false, 1, true, false",  // currentUser: owner, viewUser: admin
            "0, false, 1, false, true",  // currentUser: owner, viewUser: participant
            "1, true, 1, false, false",  // currentUser: admin, viewUser: owner
            "2, true, 1, true, false",   // currentUser: admin, viewUser: admin
            "2, true, 1, false, true",   // currentUser: admin, viewUser: participant
            "2, false, 2, false, false", // currentUser: participant, viewUser: owner
            "2, false, 1, true, false",  // currentUser: participant, viewUser: admin
            "2, false, 1, false, false"
    })
    void itShouldMakeCurrentUserAdmin(int bookOwnerUserIndex, boolean currentUserIsAdmin, int participationUserIndex, boolean participantIsAdmin, boolean expected) {
        // given
        Book book = new Book("Title", "Description", users.get(bookOwnerUserIndex), false);
        Participation currentUserParticipation = new Participation(userOne, currentUserIsAdmin);
        book.getParticipations().add(currentUserParticipation);
        currentUserParticipation.setBook(book);

        Participation participation = new Participation(users.get(participationUserIndex), participantIsAdmin);
        book.getParticipations().add(participation);
        participation.setBook(book);

        // when
        boolean currentUserCanMakeAdmin = participation.currentUserCanMakeAdmin();

        // then
        assertThat(currentUserCanMakeAdmin).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "0, false, 0, false, false", // currentUser: owner, viewUser: owner
            "0, false, 1, true, true",  // currentUser: owner, viewUser: admin
            "0, false, 1, false, false",  // currentUser: owner, viewUser: participant
            "1, true, 1, false, false",  // currentUser: admin, viewUser: owner
            "2, true, 1, true, false",   // currentUser: admin, viewUser: admin
            "2, true, 1, false, false",   // currentUser: admin, viewUser: participant
            "2, false, 2, false, false", // currentUser: participant, viewUser: owner
            "2, false, 1, true, false",  // currentUser: participant, viewUser: admin
            "2, false, 1, false, false"
    })
    void itShouldMakeCurrentUserNotAdmin(int bookOwnerUserIndex, boolean currentUserIsAdmin, int participationUserIndex, boolean participantIsAdmin, boolean expected) {
        // given
        Book book = new Book("Title", "Description", users.get(bookOwnerUserIndex), false);
        Participation currentUserParticipation = new Participation(userOne, currentUserIsAdmin);
        book.getParticipations().add(currentUserParticipation);
        currentUserParticipation.setBook(book);

        Participation participation = new Participation(users.get(participationUserIndex), participantIsAdmin);
        book.getParticipations().add(participation);
        participation.setBook(book);

        // when
        boolean currentUserCanMakeNotAdmin = participation.currentUserCanMakeNotAdmin();

        // then
        assertThat(currentUserCanMakeNotAdmin).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "0, false, 0, false, true", // currentUser: owner, viewUser: owner
            "0, false, 1, true, true",  // currentUser: owner, viewUser: admin
            "0, false, 1, false, true",  // currentUser: owner, viewUser: participant
            "1, true, 1, false, false",  // currentUser: admin, viewUser: owner
            "2, true, 1, true, false",   // currentUser: admin, viewUser: admin
            "2, true, 1, false, false",   // currentUser: admin, viewUser: participant
            "2, false, 2, false, false", // currentUser: participant, viewUser: owner
            "2, false, 1, true, false",  // currentUser: participant, viewUser: admin
            "2, false, 1, false, false"
    })
    void itShouldDeleteParticipant(int bookOwnerUserIndex, boolean currentUserIsAdmin, int participationUserIndex, boolean participantIsAdmin, boolean expected) {
        // given
        Book book = new Book("Title", "Description", users.get(bookOwnerUserIndex), false);
        Participation currentUserParticipation = new Participation(userOne, currentUserIsAdmin);
        book.getParticipations().add(currentUserParticipation);
        currentUserParticipation.setBook(book);

        Participation participation = new Participation(users.get(participationUserIndex), participantIsAdmin);
        book.getParticipations().add(participation);
        participation.setBook(book);

        // when
        boolean currentUserCanDelete = participation.currentUserCanDelete();

        // then
        assertThat(currentUserCanDelete).isEqualTo(expected);
    }
}
