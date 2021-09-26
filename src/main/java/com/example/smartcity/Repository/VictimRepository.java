package com.example.smartcity.Repository;

import com.example.smartcity.Entity.Victim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VictimRepository extends JpaRepository<Victim, UUID> {
    Optional<Victim> findByCardNumber(Long cardNumber);
}