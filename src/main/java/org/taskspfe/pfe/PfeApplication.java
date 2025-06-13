package org.taskspfe.pfe;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.taskspfe.pfe.model.role.Role;
import org.taskspfe.pfe.model.user.UserEntity;
import org.taskspfe.pfe.repository.RoleRepository;
import org.taskspfe.pfe.repository.UserEntityRepository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;

@SpringBootApplication
@RequiredArgsConstructor
public class PfeApplication {

    private final RoleRepository roleRepository;
    private final UserEntityRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(PfeApplication.class, args);
    }

    @PostConstruct
    private void initProject()
    {
        initRoles();
        initUsersWithRoles();
    }
    private void initRoles()
    {
        if (roleRepository.count() > 0) {
            return; // Roles already initialized
        }
        roleRepository.save(new Role("ADMIN"));
        roleRepository.save(new Role("CLIENT"));
        roleRepository.save(new Role("TECHNICIAN"));
        roleRepository.save(new Role("SUPERVISOR"));



    }

    public void initUsersWithRoles() {
        if (userRepository.count() > 0) {
            return; // Roles already initialized
        }

        Role adminRole = roleRepository.fetchRoleByName("ADMIN").orElse(null);
        Role clientRole = roleRepository.fetchRoleByName("CLIENT").orElse(null);
        Role technicianRole = roleRepository.fetchRoleByName("TECHNICIAN").orElse(null);
        Role supervisorRole = roleRepository.fetchRoleByName("SUPERVISOR").orElse(null);

        LocalDateTime now = LocalDateTime.now();

        userRepository.save(UserEntity.builder()
                .firstName("Alice")
                .lastName("Admin")
                .email("admin@example.com")
                .phoneNumber("1111111111")
                .password(passwordEncoder.encode("changeme")) // Make sure to encode it using PasswordEncoder
                .createdAt(now)
                .isEnabled(true)
                .role(adminRole)
                .build());

        userRepository.save(UserEntity.builder()
                .firstName("Bob")
                .lastName("Client")
                .email("client@example.com")
                .phoneNumber("2222222222")
                .password(passwordEncoder.encode("changeme"))
                .createdAt(now)
                .isEnabled(true)
                .role(clientRole)
                .build());

        userRepository.save(UserEntity.builder()
                .firstName("Charlie")
                .lastName("Technician")
                .email("technician@example.com")
                .phoneNumber("3333333333")
                .password(passwordEncoder.encode("changeme"))
                .createdAt(now)
                .isEnabled(true)
                .role(technicianRole)
                .build());

        userRepository.save(UserEntity.builder()
                .firstName("Diana")
                .lastName("Supervisor")
                .email("supervisor@example.com")
                .phoneNumber("4444444444")
                .password(passwordEncoder.encode("changeme"))
                .createdAt(now)
                .isEnabled(true)
                .role(supervisorRole)
                .build());
    }
}
