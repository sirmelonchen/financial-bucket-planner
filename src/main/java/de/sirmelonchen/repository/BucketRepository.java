package de.sirmelonchen.repository;

import de.sirmelonchen.model.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
}
