package com.example.smartcity.Repository;

import com.example.smartcity.Entity.Officer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OfficerRepository extends JpaRepository<Officer, UUID> {
    Optional<Officer> findByCardNumber(long cardNumber);
}
