package de.pomc.yearbook.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserTest {

    User userOne;

    @BeforeEach
    void setUp() {
        userOne = new User("User", "One", "user.one@gmail.com", "1234");

        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    void itShouldGetCurrentUserName() {
        // given
        UserDetails userDetails = new UserAdapter(userOne);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // when
        String username = User.getCurrentUsername();

        // then
        assertThat(username).isEqualTo("user.one@gmail.com");
    }

    @Test
    void itShouldNotGetCurrentUserName() {
        // given -> setup
        // when
        String username = User.getCurrentUsername();

        // then
        assertThat(username).isNull();
    }

    @Test
    void itShouldGetFullName() {
        // given -> setup
        // when
        String fullName = userOne.getFullName();

        //then
        assertThat(fullName).isEqualTo("User One");
    }
}
