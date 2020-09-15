package de.pomc.yearbook.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    private String email = "first.last@mail.com";
    private User user = new User("First", "Last", email, "1234");

    @BeforeEach
    void setUp() {
        userRepository.delete(user);
        userRepository.save(user);

        // inject User into authentication object
        UserDetails userDetails = new UserAdapter(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void itShouldFindCurrentUser() {
        // given
        // when
        User foundUser = userService.findCurrentUser();

        // then
        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    void itShouldFindUserByEmail() {
        // given
        // when
        User foundUser = userService.findUserByEmail(email);

        assertThat(foundUser).isEqualTo(user);
    }
}
