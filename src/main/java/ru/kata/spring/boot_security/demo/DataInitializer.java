package ru.kata.spring.boot_security.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс DataInitializer инициализирует базу данных при запуске приложения.
 * <p>
 * Создаёт роли и начального администратора для проверки работоспособности.
 * </p>
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    //ДОБАВИЛ
    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор для {@code DataInitializer}.
     *
     * @param userRepository репозиторий для работы с пользователями
     * @param roleRepository репозиторий для работы с ролями
     */
    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Метод, выполняющийся при запуске приложения.
     *
     * @param args аргументы командной строки
     * @throws Exception если происходит ошибка инициализации
     */
    @Override
    public void run(String... args) throws Exception {
        // Проверка наличия ролей в базе данных
        if (roleRepository.findByName("ROLE_USER") == null) {
            roleRepository.save(new Role("ROLE_USER"));
        }

        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }

        // Проверка наличия администратора
        if (userRepository.findByUsername("admin") == null) {
            // Получение ролей ADMIN и USER
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            Role userRole = roleRepository.findByName("ROLE_USER");

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            roles.add(userRole); // Администратор может иметь обе роли

            // Хеширование пароля администратора
            String encodedPassword = passwordEncoder.encode("admin");
            User admin = new User("admin", "admin@example.com", encodedPassword, roles);
            userRepository.save(admin);

            // Создание администратора
//            User admin = new User("admin", "admin@example.com", "admin", roles);
//            userRepository.save(admin);


        }
    }
}
