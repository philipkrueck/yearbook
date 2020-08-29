package de.pomc.yearbook.user;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        if (!findAll().isEmpty()) {
            // prevent initialization if DB is not empty
            return;
        }
        createUser((long) 0, "Frodo Baggins", "frodo.baggins@shire.com", "1234", "ADMIN");
        createUser((long) 1, "Samwise Gamgee", "sam.gamgee@shire.com", "1234", "USER");
        createUser((long) 2, "Gandalf the Gray", "gandalf.gray@hotmail.com", "1234", "USER");
        createUser((long) 3, "Legolas", "legolas@woodland.com", "1234", "USER");
        createUser((long) 4, "Gimli the Dwarf", "gimli.dwarf@blueMountain.com", "1234", "USER");
    }

    private void createUser(long id, String name, String email, String password, String role) {
        userRepository.save(new User(id, name, email, passwordEncoder.encode(password), role));
    }

    public User save(User user) {return userRepository.save(user); }

    public List<User> findAll() { return userRepository.findAll(); }

    public User findCurrentUser() { return userRepository.findByEmail(User.getCurrentUsername()); }

    public User findUserByEmail(String email) { return userRepository.findByEmail(email); }
}
