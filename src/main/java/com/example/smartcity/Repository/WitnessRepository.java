package com.example.smartcity.Repository;

import com.example.smartcity.Entity.Witness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WitnessRepository extends JpaRepository<Witness, UUID> {
    Optional<Witness> findByCardNumber(Long cardNumber);
}
