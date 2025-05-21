package de.sirmelonchen.cron;

import de.sirmelonchen.model.ConfirmationToken;
import de.sirmelonchen.repository.ConfirmationTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class jobs {
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Scheduled(cron = "0 0 * * * *") // jede Stunde
    public void deleteExpiredTokens() {
        List<ConfirmationToken> expiredTokens = confirmationTokenRepository.findAll()
                .stream()
                .filter(token -> token.getExpiresAt().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());

        confirmationTokenRepository.deleteAll(expiredTokens);
    }

}
