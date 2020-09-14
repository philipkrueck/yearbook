package de.pomc.yearbook.user;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@MockitoSettings
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private String email = "first.last@mail.com";
    private User givenUser = new User("first", "last", email, "1234");

    @Test
    void itShouldFindUserByEmail() {
        // given
        given(userRepository.findByEmail(email)).willReturn(givenUser);

        // when
        User foundUser = userService.findUserByEmail(email);

        // then
        assertThat(foundUser).isEqualTo(givenUser);
        verify(userRepository, only()).findByEmail(email);
    }

    @Test
    void itShouldFindCurrentUser() {
        // given
        // inject User into authentication object
        UserDetails userDetails = new UserAdapter(givenUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        given(userRepository.findByEmail(email)).willReturn(givenUser);

        // when
        User foundUser = userService.findCurrentUser();

        // then
        assertThat(foundUser).isEqualTo(givenUser);
        verify(userRepository, only()).findByEmail(email);
    }
}
