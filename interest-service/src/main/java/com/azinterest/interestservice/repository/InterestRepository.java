package com.azinterest.interestservice.repository;

import com.azinterest.interestservice.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InterestRepository extends JpaRepository<Interest, UUID> {
    boolean existsByName(String name);

    boolean existsBySlug(String slug);
}
