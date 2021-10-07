package com.example.smartcity.repository;

import com.example.smartcity.entity.enums.RoleName;
import com.example.smartcity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(RoleName roleName);
}
