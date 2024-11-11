package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Контроллер AdminController отвечает за управление пользователями.
 * <p>
 * Доступен только пользователям с ролью ADMIN. Предоставляет CRUD операции для пользователей.
 * </p>
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    /**
     * Конструктор для {@code AdminController}.
     *
     * @param userService сервис для управления пользователями
     */
    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    /**
     * Отображает страницу с перечнем всех пользователей.
     *
     * @param model объект {@link Model} для передачи данных в представление
     * @return имя представления "admin.html"
     */
    @GetMapping
    public String allUsers(Model model) {
        List<User> users = userService.findAll(); // Получение списка всех пользователей
        model.addAttribute("users", users); // Добавление пользователей в модель
        return "admin/admin";
    }

    /**
     * Отображает форму для добавления нового пользователя.
     *
     * @param model объект {@link Model} для передачи данных в представление
     * @return имя представления "edit_user.html" для добавления
     */
    @GetMapping("/new")
    public String newUser(Model model) {
        User user = new User();
        user.setRoles(new HashSet<>()); // Инициализация здесь
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleRepository.findAll());
        return "admin/edit_user";
    }

    /**
     * Обрабатывает POST-запрос для добавления нового пользователя.
     *
     * @param user объект {@link User}, полученный из формы
     * @return перенаправление на страницу списка пользователей
     */
    @PostMapping
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam("roles") List<Long> roles) {
        Set<Role> userRoles = roleRepository.findAllById(roles).stream().collect(Collectors.toSet());
        user.setRoles(userRoles); // Назначение выбранных ролей пользователю
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);// Сохранение нового пользователя
        return "redirect:/admin";
    }

    /**
     * Отображает форму для редактирования существующего пользователя.
     *
     * @param id    идентификатор пользователя
     * @param model объект {@link Model} для передачи данных в представление
     * @return имя представления "edit_user.html" для редактирования
     */
    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            // Обработка случая, когда пользователь не найден
            return "redirect:/admin";
        }
        model.addAttribute("user", user); // Добавление пользователя в модель для редактирования
        model.addAttribute("allRoles", roleRepository.findAll()); // Добавление всех ролей в модель
        return "admin/edit_user";
    }

    /**
     * Обрабатывает POST-запрос для обновления существующего пользователя.
     *
     * @param user объект {@link User}, полученный из формы
     * @return перенаправление на страницу списка пользователей
     */
    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute("user") User user,
                             @RequestParam("roles") List<Long> roles) {
        User existingUser = userService.findById(id);
        if (existingUser == null) {
            return "redirect:/admin";
        }

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        Set<Role> userRoles = roleRepository.findAllById(roles).stream().collect(Collectors.toSet());
        existingUser.setRoles(userRoles);

        userService.update(existingUser);
        return "redirect:/admin";
    }


    /**
     * Обрабатывает запрос на удаление пользователя.
     *
     * @param id идентификатор пользователя
     * @return перенаправление на страницу списка пользователей
     */
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id); // Удаление пользователя
        return "redirect:/admin";
    }
}
