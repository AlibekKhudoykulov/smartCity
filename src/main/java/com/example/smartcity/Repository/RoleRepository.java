package com.example.smartcity.Repository;

import com.example.smartcity.Entity.Enums.RoleName;
import com.example.smartcity.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByRoleName(RoleName roleName);
}
