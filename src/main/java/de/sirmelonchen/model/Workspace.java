package de.sirmelonchen.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Workspace.
 */
@Entity
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id") // Fremdschlüssel
    private User user;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bucket> buckets = new ArrayList<>();

    private String ownerUsername;

    /**
     * Instantiates a new Workspace.
     */
    public Workspace() {
    }

    /**
     * Gets owner username.
     *
     * @return the owner username
     */
    public String getOwnerUsername() {
        return this.user.getUsername();
    }

    /**
     * Sets owner username.
     *
     * @param ownerUsername the owner username
     */
    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    /**
     * Instantiates a new Workspace.
     *
     * @param name the name
     * @param user the user
     */
    public Workspace(String name, User user) {
        this.name = name;
        this.user = user;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets buckets.
     *
     * @return the buckets
     */
    public List<Bucket> getBuckets() {
        return buckets;
    }

    /**
     * Sets buckets.
     *
     * @param buckets the buckets
     */
    public void setBuckets(List<Bucket> buckets) {
        this.buckets = buckets;
    }
}
