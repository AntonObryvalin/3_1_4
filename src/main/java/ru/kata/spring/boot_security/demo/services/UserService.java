package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    User findByUsername(String username);

    List<User> findAll();

    void save(User user);

    void update(User user, String newPassword);

    void delete(Long id);

    User findByEmail(String email);


}
