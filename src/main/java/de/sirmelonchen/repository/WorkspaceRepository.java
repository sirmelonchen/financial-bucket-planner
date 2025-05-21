package de.sirmelonchen.repository;

import de.sirmelonchen.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Workspace repository.
 */
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    /**
     * Find all by user username list.
     *
     * @param username the username
     * @return the list
     */
    List<Workspace> findAllByUserUsername(String username);

    List<Workspace> findByUserId(Long userId);
}
