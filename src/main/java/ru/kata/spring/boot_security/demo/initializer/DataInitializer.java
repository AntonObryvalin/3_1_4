//package ru.kata.spring.boot_security.demo.initializer;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import ru.kata.spring.boot_security.demo.models.Role;
//import ru.kata.spring.boot_security.demo.models.User;
//import ru.kata.spring.boot_security.demo.services.RoleService;
//import ru.kata.spring.boot_security.demo.services.UserService;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    private final UserService userService;
//    private final RoleService roleService;
//    private final PasswordEncoder passwordEncoder;
//
//    public DataInitializer(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
//        this.userService = userService;
//        this.roleService = roleService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void run(String... args) {
//        // Инициализация ролей через сервисный слой
//        if (roleService.findByName("ROLE_USER") == null) {
//            roleService.save(new Role("ROLE_USER"));
//        }
//
//        if (roleService.findByName("ROLE_ADMIN") == null) {
//            roleService.save(new Role("ROLE_ADMIN"));
//        }
//
//        // Инициализация администратора через сервисный слой
//        if (userService.findByUsername("admin") == null) {
//            Role adminRole = roleService.findByName("ROLE_ADMIN");
//            Role userRole = roleService.findByName("ROLE_USER");
//            Set<Role> roles = new HashSet<>();
//            roles.add(adminRole);
//            roles.add(userRole);
//
//            String encodedPassword = passwordEncoder.encode("admin");
//            User admin = new User("admin", "admin@example.com", encodedPassword, roles);
//            userService.save(admin);
//        }
//    }
//}

package ru.kata.spring.boot_security.demo.initializer;

import org.hibernate.Hibernate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional; // Добавьте эту импорт
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
    @Transactional // Добавляем эту аннотацию
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


