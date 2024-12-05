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


//package ru.kata.spring.boot_security.demo.controllers;
//
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_security.demo.models.User;
//import ru.kata.spring.boot_security.demo.models.Role;
//import ru.kata.spring.boot_security.demo.services.UserService;
//import ru.kata.spring.boot_security.demo.services.RoleService;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/users")
//public class UserRestController {
//
//    private final UserService userService;
//    private final RoleService roleService;
//
//    public UserRestController(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//    // Получить список всех пользователей
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<User>> getAllUsers() {
//        return ResponseEntity.ok(userService.findAll());
//    }
//
//    // Получить пользователя по ID
//    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        User user = userService.findById(id);
//        if (user == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(user);
//    }
//
//    // Создать нового пользователя
//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        // Получаем все роли из базы данных
//        List<Role> allRoles = roleService.findAll();
//
//        // Сопоставляем роли пользователя с реальными объектами Role
//        Set<Role> userRoles = user.getRoles().stream()
//                .map(role -> allRoles.stream()
//                        .filter(r -> r.getId().equals(role.getId()))
//                        .findFirst()
//                        .orElse(null))
//                .collect(Collectors.toSet());
//
//        user.setRoles(userRoles);
//
//        userService.save(user);
//        return ResponseEntity.ok(user);
//    }
//
//    // Обновить существующего пользователя
//    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
//        User existingUser = userService.findById(id);
//        if (existingUser == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // Получаем все роли из базы данных
//        List<Role> allRoles = roleService.findAll();
//
//        // Сопоставляем роли пользователя с реальными объектами Role
//        Set<Role> userRoles = user.getRoles().stream()
//                .map(role -> allRoles.stream()
//                        .filter(r -> r.getId().equals(role.getId()))
//                        .findFirst()
//                        .orElse(null))
//                .collect(Collectors.toSet());
//
//        user.setRoles(userRoles);
//
//        // Устанавливаем ID пользователя
//        user.setId(id);
//
//        // Обновляем пользователя
//        userService.update(user, user.getPassword());
//
//        return ResponseEntity.ok(user);
//    }
//
//    // Удалить пользователя
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//        userService.delete(id);
//        return ResponseEntity.ok().build();
//    }
//}
