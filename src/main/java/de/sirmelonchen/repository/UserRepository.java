package de.sirmelonchen.repository;

import de.sirmelonchen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * The interface User repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find by username optional.
     *
     * @param username the username
     * @return the optional
     */
    Optional<User> findByUsername(String username);
}
