package de.sirmelonchen.service;

import de.sirmelonchen.model.Bucket;
import de.sirmelonchen.model.Workspace;
import de.sirmelonchen.repository.BucketRepository;
import de.sirmelonchen.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;

@Service
public class BucketService {

    private final BucketRepository bucketRepository;
    private final WorkspaceRepository workspaceRepository;

    public BucketService(BucketRepository bucketRepository, WorkspaceRepository workspaceRepository) {
        this.bucketRepository = bucketRepository;
        this.workspaceRepository = workspaceRepository;
    }

    public void addBucketToWorkspace(Long workspaceId, String name, BigDecimal amount) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new IllegalArgumentException("Workspace nicht gefunden"));

        Bucket bucket = new Bucket();
        bucket.setName(name);
        bucket.setAmount(amount);
        bucket.setWorkspace(workspace);

        bucketRepository.save(bucket);
    }

    public void deleteExpense(Long bucketId, Long expenseId, String username) {
        Bucket bucket = bucketRepository.findById(bucketId)
                .orElseThrow(() -> new RuntimeException("Bucket nicht gefunden"));

        // Optional: prüfen, ob der Nutzer Zugriff auf diesen Bucket hat
        if (!bucket.getWorkspace().getUser().getUsername().equals(username)) {
            throw new RuntimeException("Zugriff verweigert");
        }

        bucket.getExpenses().removeIf(expense -> expense.getId().equals(expenseId));
        bucketRepository.save(bucket); // oder expenseRepository.deleteById(expenseId);
    }

    public void deleteBucket(Long bucketId, String username) {
        Bucket bucket = bucketRepository.findById(bucketId)
                .orElseThrow(() -> new IllegalArgumentException("Bucket nicht gefunden"));

        // Optional: prüfen, ob der Benutzer Besitzer des Buckets ist
        if (!bucket.getWorkspace().getUser().getUsername().equals(username)) {
            throw new RuntimeException("Zugriff verweigert");
        }

        bucketRepository.delete(bucket);
    }

}
