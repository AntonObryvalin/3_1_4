package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // Получить список всех пользователей
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    // Получить пользователя по ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    // Создать нового пользователя
    @PostMapping
    public User createUser(@RequestBody User user) {
        userService.save(user);
        return user;
    }

    // Обновить существующего пользователя
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userService.update(user, user.getPassword());
        return user;
    }

    // Удалить пользователя
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
