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

    public User save(User user) { return userRepository.save(user); }

    public List<User> findAll() { return userRepository.findAll(); }

    public User findCurrentUser() { return userRepository.findByEmail(User.getCurrentUsername()); }

    public User findUserByEmail(String email) { return userRepository.findByEmail(email); }
}
