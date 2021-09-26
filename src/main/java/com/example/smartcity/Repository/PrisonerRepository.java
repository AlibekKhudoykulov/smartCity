package com.example.smartcity.Repository;

import com.example.smartcity.Entity.Prisoner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PrisonerRepository extends JpaRepository<Prisoner, UUID> {
}
