package de.pomc.yearbook.participation;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserAdapter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@MockitoSettings
public class ParticipationServiceTest {

    @Mock
    private ParticipationRepository participationRepository;

    @InjectMocks
    private ParticipationService participationService;

    @Test
    void itShouldGetParticipationsOfCurrentUser() {
        // given
        String userEmail = "first.last@mail.com";
        User user = new User("First", "Last", userEmail, "1234");

        // inject User into authentication object
        UserDetails userDetails = new UserAdapter(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<Participation> givenParticipations = List.of(new Participation(user, true));
        given(participationRepository.findParticipationByParticipant_Email(userEmail)).willReturn(givenParticipations);

        // when
        List<Participation> foundParticipationsOfCurrentUser = participationService.getParticipationsOfCurrentUser();

        // then
        assertThat(foundParticipationsOfCurrentUser).isEqualTo(givenParticipations);
        verify(participationRepository, never()).findAll();
    }
}
