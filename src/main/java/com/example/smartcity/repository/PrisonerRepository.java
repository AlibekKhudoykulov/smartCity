package com.example.smartcity.repository;

import com.example.smartcity.entity.Prisoner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PrisonerRepository extends JpaRepository<Prisoner, UUID> {
    Optional<Prisoner> findByCardNumber(Long cardNumber);

    Optional<Prisoner> findByCardNumberAndIdNot(long cardNumber,UUID id);
}
