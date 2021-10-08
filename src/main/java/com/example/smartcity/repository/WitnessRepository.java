package com.example.smartcity.repository;

import com.example.smartcity.entity.Witness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WitnessRepository extends JpaRepository<Witness, UUID> {
    Optional<Witness> findByCardNumber(Long cardNumber);
    boolean existsByCardNumber(Long cardNumber);
    boolean existsByCardNumberAndIdNot(Long cardNumber, UUID id);
}
