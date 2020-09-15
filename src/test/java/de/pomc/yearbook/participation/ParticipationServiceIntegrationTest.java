package de.pomc.yearbook.participation;

import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ParticipationServiceIntegrationTest {

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private ParticipationRepository participationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private User userOne;
    private User userTwo;

    @BeforeEach
    void setUp() {
        participationRepository.deleteAll();

        userOne = new User("First", "Last", "first.last@mail.com", "1234");
        userTwo = new User("First", "Last", "anotherEmail", "1234");
        entityManager.persist(userOne);
        entityManager.persist(userTwo);

        // inject User into authentication object
        UserDetails userDetails = new UserAdapter(userOne);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void itShouldGetParticipationOfCurrentUser() {
        // given
        Participation participationOne = buildParticipationWithParticipant(userOne);
        Participation participationTwo = buildParticipationWithParticipant(userOne);
        Participation participationThree = buildParticipationWithParticipant(userTwo); // should not be found

        // when
        List<Participation> foundParticipationsOfCurrentUser = participationService.getParticipationsOfCurrentUser();

        // then
        assertThat(foundParticipationsOfCurrentUser).containsExactlyInAnyOrder(participationOne, participationTwo);
    }

    private Participation buildParticipationWithParticipant(User participant) {
        return participationService.save(new Participation(participant, false));
    }
}
