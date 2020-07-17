package de.pomc.yearbook.user;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        if (!findAll().isEmpty()) {
            // prevent initialization if DB is not empty
            return;
        }
        createUser("Philip");
        createUser("Oliver");
        createUser("Malte");
        createUser("Christian");
    }

    private void createUser(String name) {
        userRepository.save(new User(name));
    }

    public List<User> findAll() { return userRepository.findAll(); }
}
