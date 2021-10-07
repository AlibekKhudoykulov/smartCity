package com.example.smartcity.repository;

import com.example.smartcity.entity.Crime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CrimeRepository extends JpaRepository<Crime, UUID> {
}
