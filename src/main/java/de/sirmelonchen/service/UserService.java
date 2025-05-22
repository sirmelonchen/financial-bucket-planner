package de.sirmelonchen.service;

import de.sirmelonchen.model.ConfirmationToken;
import de.sirmelonchen.model.User;
import de.sirmelonchen.repository.ConfirmationTokenRepository;
import de.sirmelonchen.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The type User service.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository  the user repository
     * @param tokenRepository the token repository
     * @param passwordEncoder the password encoder
     * @param emailService    the email service
     */
    public UserService(UserRepository userRepository,
                       ConfirmationTokenRepository tokenRepository,
                       PasswordEncoder passwordEncoder,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    /**
     * Register user.
     *
     * @param username    the username
     * @param rawPassword the raw password
     */
    public void registerUser(String username, String rawPassword, String email) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(username, encodedPassword, "USER", email);
        user.setEnabled(false); // noch nicht aktiviert
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        confirmationToken.setUser(user);
        tokenRepository.save(confirmationToken);

        String link = "http://localhost:8080/confirm?token=" + token;
        String html = "<h3>Willkommen!</h3>" +
                "<p>Bitte bestätige deine E-Mail-Adresse:</p>" +
                "<a href=\"" + link + "\"><button>Bestätigen</button></a>";

        emailService.send(user.getEmail(), "Bitte bestätige deine E-Mail Adresse", html);
    }

    /**
     * Confirm user.
     *
     * @param token the token
     */
    public void confirmUser(String token) {
        ConfirmationToken confirmationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token ungültig"));

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token abgelaufen");
        }

        User user = confirmationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        tokenRepository.delete(confirmationToken);
        String link = "http://localhost:8080/login";
        String html = "<h3>Dein Konto wurde erfolgreich aktiviert!</h3>" +
                "<p>Du hast dein Konto erfolgreich aktiviert.:</p>" +
                "<a href=\"" + link + "\"><button>Login</button></a>";

        emailService.send(user.getEmail(), "Konto Aktiviert", html);// Optional: Token löschen
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }


    /**
     * Delete user.
     *
     * @param userId the user id
     */
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


}
