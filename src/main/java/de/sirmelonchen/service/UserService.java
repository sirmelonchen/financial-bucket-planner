package de.sirmelonchen.service;

import de.sirmelonchen.model.User;
import de.sirmelonchen.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(username, encodedPassword, "USER");
        userRepository.save(user);
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void lockUser(Long userId) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setLocked(true);
            userRepository.save(user);
        });
    }

    public void unlockUser(Long userId) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setLocked(false);
            userRepository.save(user);
        });
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


}
