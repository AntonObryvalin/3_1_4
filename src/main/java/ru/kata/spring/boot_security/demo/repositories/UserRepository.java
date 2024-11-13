package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;

public interface UserRepository {
    User findById(Long id);
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findAll();
    void save(User user);
    void update(User user);
    void delete(Long id);
}
