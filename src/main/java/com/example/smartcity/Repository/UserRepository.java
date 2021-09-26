package com.example.smartcity.Repository;

import com.example.smartcity.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByCardNumber(Long cardNumber);
    boolean existsByUsernameAndIdNot(String username, UUID id);
}
