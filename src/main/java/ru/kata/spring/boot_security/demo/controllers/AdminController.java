//package ru.kata.spring.boot_security.demo.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_security.demo.models.Role;
//import ru.kata.spring.boot_security.demo.models.User;
//import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
//import ru.kata.spring.boot_security.demo.services.RoleService;
//import ru.kata.spring.boot_security.demo.services.UserService;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//
//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
//    private final RoleService roleService;
//
//
//    public AdminController(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//        this.roleService = roleService;
//
//    }
//
//    @GetMapping
//    public String allUsers(Model model) {
//        List<User> users = userService.findAll(); // Получение списка всех пользователей
//        model.addAttribute("users", users); // Добавление пользователей в модель
//        return "admin/admin";
//    }
//
//    @GetMapping("/new")
//    public String newUser(Model model) {
//        User user = new User();
//        user.setRoles(new HashSet<>()); // Инициализация здесь
//        model.addAttribute("user", user);
//        model.addAttribute("allRoles", roleService.findAll());
//        return "admin/edit_user";
//    }
//
//    @PostMapping
//    public String addUser(@ModelAttribute("user") User user,
//                          @RequestParam("roles") List<Long> roles) {
//        Set<Role> userRoles = new HashSet<>(roleService.findAllById(roles));
//        user.setRoles(userRoles); // Назначение выбранных ролей пользователю
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userService.save(user);// Сохранение нового пользователя
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String editUser(@PathVariable("id") Long id, Model model) {
//        User user = userService.findById(id);
//        if (user == null) {
//            // Обработка случая, когда пользователь не найден
//            return "redirect:/admin";
//        }
//        model.addAttribute("user", user); // Добавление пользователя в модель для редактирования
//        model.addAttribute("allRoles", roleService.findAll()); // Добавление всех ролей в модель
//        return "admin/edit_user";
//    }
//
//    @PostMapping("/{id}/update")
//    public String updateUser(@PathVariable("id") Long id,
//                             @ModelAttribute("user") User user,
//                             @RequestParam("roles") List<Long> roles,
//                             Model model) {
//
//        User existingUser = userService.findById(id);
//        if (existingUser == null) {
//            return "redirect:/admin";
//        }
//
//        // Проверка, существует ли пользователь с таким же email
//        User userByEmail = userService.findByEmail(user.getEmail());
//        if (userByEmail != null && !userByEmail.getId().equals(existingUser.getId())) {
//            model.addAttribute("error", "Пользователь с таким email уже существует");
//            model.addAttribute("user", existingUser);
//            model.addAttribute("allRoles", roleService.findAll());
//            return "admin/edit_user";
//        }
//
//        existingUser.setUsername(user.getUsername());
//        existingUser.setEmail(user.getEmail());
//
//        // Если пароль пустой, оставляем старый пароль
//        if (user.getPassword() == null || user.getPassword().isEmpty()) {
//            user.setPassword(existingUser.getPassword());
//        } else {
//            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
//        }
//
//        Set<Role> userRoles = new HashSet<>(roleService.findAllById(roles));
//        existingUser.setRoles(userRoles);
//
//        userService.update(existingUser);
//        return "redirect:/admin";
//    }
//
//
//
//
//    @PostMapping("/{id}/delete")
//    public String deleteUser(@PathVariable("id") Long id) {
//        userService.delete(id); // Удаление пользователя
//        return "redirect:/admin";
//    }
//}

package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String allUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/admin";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/edit_user";
    }

    @PostMapping
    public String addUser(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roles) {
        Set<Role> userRoles = new HashSet<>(roleService.findAllById(roles));
        user.setRoles(userRoles);
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/admin";
        }
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/edit_user";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user, @RequestParam("roles") List<Long> roles, Model model) {
        User existingUser = userService.findById(id);
        if (existingUser == null) {
            return "redirect:/admin";
        }

        User userByEmail = userService.findByEmail(user.getEmail());
        if (userByEmail != null && !userByEmail.getId().equals(existingUser.getId())) {
            model.addAttribute("error", "Пользователь с таким email уже существует");
            model.addAttribute("user", existingUser);
            model.addAttribute("allRoles", roleService.findAll());
            return "admin/edit_user";
        }

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setRoles(new HashSet<>(roleService.findAllById(roles)));

        // Передаём обновлённого пользователя в сервис, где происходит проверка и шифрование пароля
        userService.update(existingUser, user.getPassword());
        return "redirect:/admin";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
