package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // получить список всех пользователей
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    // получить пользователя по ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    // эндпоинт для получения текущего пользователя
    @GetMapping("/current")
    public User getCurrentUser(Principal principal) {
        if (principal == null) {
            return null; // или обработать неавторизованный доступ
        }
        return userService.findByUsername(principal.getName());
    }

    // создать новово пользователя
    @PostMapping
    public User createUser(@RequestBody User user) {
        userService.save(user);
        return user;
    }

    // абновить существующего пользователя
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userService.update(user, user.getPassword());
        return user;
    }

    // удалить пользователя
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
