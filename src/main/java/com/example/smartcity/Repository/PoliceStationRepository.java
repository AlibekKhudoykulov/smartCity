package com.example.smartcity.Repository;

import com.example.smartcity.Entity.PoliceStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PoliceStationRepository extends JpaRepository<PoliceStation, UUID> {
    Optional<PoliceStation> findByName(String name);

}
