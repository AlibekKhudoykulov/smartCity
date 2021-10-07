package com.example.smartcity.repository;

import com.example.smartcity.entity.Officer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OfficerRepository extends JpaRepository<Officer, UUID> {
    Optional<Officer> findByCardNumber(long cardNumber);
}
