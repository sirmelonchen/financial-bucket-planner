package de.sirmelonchen.repository;

import de.sirmelonchen.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Confirmation token repository.
 */
@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    /**
     * Find by token optional.
     *
     * @param token the token
     * @return the optional
     */
    Optional<ConfirmationToken> findByToken(String token);

    /**
     * Delete by token.
     *
     * @param token the token
     */
    void deleteByToken(String token);
}
