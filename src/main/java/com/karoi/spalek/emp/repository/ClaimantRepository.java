package com.karoi.spalek.emp.repository;

import com.karoi.spalek.emp.model.Claimant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClaimantRepository extends JpaRepository<Claimant, Long> {

    Optional<Claimant> findClaimantByIdentifier(String identifier);
}

