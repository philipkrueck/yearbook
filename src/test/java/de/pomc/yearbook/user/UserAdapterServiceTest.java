package de.pomc.yearbook.user;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@MockitoSettings
public class UserAdapterServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserAdapterService userAdapterService;

    @Test
    void itShouldAssertLoadUserByUsernameThrows() {
        // given
        String email = "non existant";
        given(userRepository.findByEmail(email)).willReturn(null);

        // when
        // then
        assertThatThrownBy(() -> userAdapterService.loadUserByUsername(email))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("Username not found for " + email);

    }

    @Test
    void itShouldAssertLoadByUsernameFindsUser() {
        // given
        String email = "first.last@gmail.com";
        User user = new User("First", "Last", email, "123");
        given(userRepository.findByEmail(email)).willReturn(user);

        // when
        UserDetails userDetails = userAdapterService.loadUserByUsername(email);

        // then
        assertThat(userDetails.getUsername()).isEqualTo(user.getEmail());
    }
}
