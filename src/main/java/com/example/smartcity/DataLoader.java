package com.example.smartcity;

import com.example.smartcity.Entity.Enums.RoleName;
import com.example.smartcity.Entity.Role;
import com.example.smartcity.Entity.User;
import com.example.smartcity.Repository.RoleRepository;
import com.example.smartcity.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    private String mode;


    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public DataLoader(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")) {
            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleName(RoleName.ROLE_ADMIN));
            User user=new User(
                    "admin",
                    "admin",
                    "12312",
                    213124l,
                    "adasd",
                    true,
                    roles
            );
            userRepository.save(user);
        }
    }
}
