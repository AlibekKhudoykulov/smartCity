package com.example.smartcity.component;

import com.example.smartcity.Entity.Enums.RoleName;
import com.example.smartcity.Entity.Role;
import com.example.smartcity.Entity.User;
import com.example.smartcity.Repository.RoleRepository;
import com.example.smartcity.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    private String mode;


    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")) {

            List<Role> roleList=new ArrayList<>();
            roleList.add(new Role(RoleName.ROLE_ADMIN));
            roleList.add(new Role(RoleName.ROLE_MANAGER));
            roleList.add(new Role(RoleName.ROLE_USER));
            roleRepository.saveAll(roleList);
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleName(RoleName.ROLE_ADMIN));
            roles.add(roleRepository.findByRoleName(RoleName.ROLE_MANAGER));
            roles.add(roleRepository.findByRoleName(RoleName.ROLE_USER));
            User user=new User(
                    "admin",
                    passwordEncoder.encode("admin"),
                    "12312",
                    213124l,
                    "adasd",
                    roles,
                    true
            );
            userRepository.save(user);
        }
    }
}
