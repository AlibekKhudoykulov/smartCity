package com.example.smartcity.repository;

import com.example.smartcity.entity.MorgueRequest;
import com.example.smartcity.entity.Officer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MorgueRequestRepository extends JpaRepository<MorgueRequest, UUID> {
    Optional<MorgueRequest> findByCorpseCardNumber(long corpseCardNumber);
    Optional<MorgueRequest> findByOfficer(Officer officer);
}
