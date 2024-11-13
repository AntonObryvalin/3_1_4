package ru.kata.spring.boot_security.demo.services;

import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        Hibernate.initialize(user.getRoles());
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User user, String newPassword) {
        // Получаем существующего пользователя из базы данных
        User existingUser = userRepository.findById(user.getId());
        if (existingUser == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        // Проверка: если новый пароль пустой, сохраняем старый пароль
        if (newPassword == null || newPassword.isEmpty()) {
            user.setPassword(existingUser.getPassword());
        } else {
            // Шифруем новый пароль
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        // Устанавливаем неизменные поля из существующего пользователя
        user.setUsername(existingUser.getUsername());
        user.setEmail(existingUser.getEmail());

        // Если роли не были изменены, сохраняем существующие
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(existingUser.getRoles());
        }

        // Обновляем пользователя через репозиторий
        userRepository.update(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
