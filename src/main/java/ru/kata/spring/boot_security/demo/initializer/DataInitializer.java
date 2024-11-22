package ru.kata.spring.boot_security.demo.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    public DataInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (roleService.findByName("ROLE_USER") == null) {
            roleService.save(new Role("ROLE_USER"));
        }

        if (roleService.findByName("ROLE_ADMIN") == null) {
            roleService.save(new Role("ROLE_ADMIN"));
        }

        if (userService.findByUsername("admin") == null) {
            Role adminRole = roleService.findByName("ROLE_ADMIN");
            Role userRole = roleService.findByName("ROLE_USER");
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            roles.add(userRole);

            User admin = new User("admin", "admin@example.com", "admin", roles);
            userService.save(admin);
        }
    }
}


