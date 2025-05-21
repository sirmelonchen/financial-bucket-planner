package de.sirmelonchen.model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Redacted workspace.
 */
public class RedactedWorkspace {
    private String name;
    private String ownerUsername;
    private List<RedactedBucket> buckets;

    /**
     * Instantiates a new Redacted workspace.
     *
     * @param workspace the workspace
     */
    public RedactedWorkspace(Workspace workspace) {
        this.name = workspace.getName();
        this.ownerUsername = workspace.getOwnerUsername();
        this.buckets = workspace.getBuckets().stream()
                .map(RedactedBucket::new)
                .collect(Collectors.toList());
    }

    /**
     * Gets name.
     *
     * @return the name
     */
// Getter
    public String getName() { return name; }

    /**
     * Gets owner username.
     *
     * @return the owner username
     */
    public String getOwnerUsername() { return ownerUsername; }

    /**
     * Gets buckets.
     *
     * @return the buckets
     */
    public List<RedactedBucket> getBuckets() { return buckets; }
}
