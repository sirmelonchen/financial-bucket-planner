package de.sirmelonchen.service;

import de.sirmelonchen.model.Bucket;
import de.sirmelonchen.model.Workspace;
import de.sirmelonchen.repository.BucketRepository;
import de.sirmelonchen.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;

@Service
public class BucketService {

    private final BucketRepository bucketRepository;
    private final WorkspaceRepository workspaceRepository;

    public BucketService(BucketRepository bucketRepository, WorkspaceRepository workspaceRepository) {
        this.bucketRepository = bucketRepository;
        this.workspaceRepository = workspaceRepository;
    }

    public void addBucketToWorkspace(Long workspaceId, String name, double amount) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new IllegalArgumentException("Workspace nicht gefunden"));

        Bucket bucket = new Bucket();
        bucket.setName(name);
        bucket.setAmount(amount);
        bucket.setWorkspace(workspace);

        bucketRepository.save(bucket);
    }
}
