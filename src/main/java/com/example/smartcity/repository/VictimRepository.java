package com.example.smartcity.repository;

import com.example.smartcity.entity.Victim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VictimRepository extends JpaRepository<Victim, UUID> {
    Optional<Victim> findByCardNumber(Long cardNumber);
    boolean existsByCardNumber(Long cardNumber);
    boolean existsByCardNumberAndIdNot(Long cardNumber, UUID id);
}
