package de.sirmelonchen.service;

import de.sirmelonchen.model.User;
import de.sirmelonchen.model.Workspace;
import de.sirmelonchen.repository.UserRepository;
import de.sirmelonchen.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;

    public WorkspaceService(WorkspaceRepository workspaceRepository, UserRepository userRepository) {
        this.workspaceRepository = workspaceRepository;
        this.userRepository = userRepository;
    }

    public List<Workspace> getWorkspacesForUser(String username) {
        return workspaceRepository.findAllByUserUsername(username);
    }

    public void createWorkspace(String username, String name) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Workspace workspace = new Workspace(name, user);
        workspaceRepository.save(workspace);
    }
    public Workspace getWorkspaceById(Long id) {
        return workspaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workspace not found"));
    }

    public void deleteById(Long id) {
        workspaceRepository.deleteById(id);
    }
}
