package com.example.smartcity.repository;

import com.example.smartcity.entity.Officer;
import com.example.smartcity.entity.enums.OfficerRank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OfficerRepository extends JpaRepository<Officer, UUID> {
    Optional<Officer> findByCardNumber(long cardNumber);
    boolean existsByCardNumberAndIdNot(long cardNumber, UUID id);
    List<Officer> findAllByRank(OfficerRank rank);
}
