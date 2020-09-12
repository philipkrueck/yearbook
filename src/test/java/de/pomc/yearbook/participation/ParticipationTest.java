package de.pomc.yearbook.participation;

import static org.assertj.core.api.Assertions.assertThat;
import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class ParticipationTest {

    User userOne;
    User userTwo;

    @BeforeEach
    void setUp() {
        userOne = new User("User", "One", "user.one@gmail.com", "1234");
        userTwo = new User("User", "Two", "user.two@gmail.com", "1234");

        // inject User into authentication object
        UserDetails userDetails = new UserAdapter(userOne);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void currentUserShouldBeParticipant() {
        // given
        User userOne = new User("User", "One", "user.one@gmail.com", "1234");
        Participation participation = new Participation(userOne, false);

        // when
        boolean currentUserIsParticipant = participation.currentUserIsParticipant();

        // then
        assertThat(currentUserIsParticipant).isTrue();
    }

    @Test
    void currentUserShouldNotBeParticipant() {
        // given
        User userOne = new User("User", "One", "user.one@gmail.com", "1234");
        Participation participation = new Participation(userOne, false);

        // when
        boolean currentUserIsParticipant = participation.currentUserIsParticipant();

        // then
        assertThat(currentUserIsParticipant).isTrue();
    }
}
