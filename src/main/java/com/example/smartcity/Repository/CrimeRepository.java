package com.example.smartcity.Repository;

import com.example.smartcity.Entity.Crime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CrimeRepository extends JpaRepository<Crime, UUID> {
}
