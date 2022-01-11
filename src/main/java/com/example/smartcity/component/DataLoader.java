package com.example.smartcity.component;

import com.example.smartcity.entity.HMACSecretKey;
import com.example.smartcity.entity.enums.RoleName;
import com.example.smartcity.entity.Role;
import com.example.smartcity.entity.User;
import com.example.smartcity.repository.HMACSecretKeyRepository;
import com.example.smartcity.repository.RoleRepository;
import com.example.smartcity.repository.UserRepository;
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
    private final HMACSecretKeyRepository hmacSecretKeyRepository;

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
                    "Albek@gmail.com",
                    roles,
                    true
            );
            userRepository.save(user);
            
            List<HMACSecretKey> hmacSecretKeys=new ArrayList<>();
            hmacSecretKeys.add(new HMACSecretKey("police","policeKey"));
            hmacSecretKeys.add(new HMACSecretKey("recreation","recreationKey"));
            hmacSecretKeys.add(new HMACSecretKey("CITY_MANAGEMENT","cityManagementSecretKey"));
            hmacSecretKeyRepository.saveAll(hmacSecretKeys);
        }
    }
}
