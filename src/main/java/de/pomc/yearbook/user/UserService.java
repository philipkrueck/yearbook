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
        save(new User((long) 0, "Frodo", "Baggins", "frodo.baggins@shire.com", passwordEncoder.encode("1234"), "USER"));
        save(new User((long) 1, "Samwise", "Gamgee", "sam.gamgee@shire.com", passwordEncoder.encode("1234"), "USER"));
        save(new User((long) 2, "Gandalf",  "the Gray", "gandalf.gray@hotmail.com", passwordEncoder.encode("1234"), "USER"));
        save(new User((long) 3, "Legolas", "Son of Thranduil", "legolas@woodland.com", passwordEncoder.encode("1234"), "USER"));
        save(new User((long) 4, "Gimli", "Son of Gloin", "gimli.dwarf@blueMountain.com", passwordEncoder.encode("1234"), "USER"));
    }

    public User save(User user) { return userRepository.save(user); }

    public List<User> findAll() { return userRepository.findAll(); }

    public User findCurrentUser() { return userRepository.findByEmail(User.getCurrentUsername()); }

    public User findUserByEmail(String email) { return userRepository.findByEmail(email); }
}
