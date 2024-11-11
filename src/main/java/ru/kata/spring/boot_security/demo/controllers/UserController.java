package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.stream.Collectors;

/**
 * Контроллер UserController отвечает за отображение данных текущего пользователя.
 * <p>
 * Доступен пользователям с ролями USER и ADMIN.
 * </p>
 */
@Controller
public class UserController {

    private final UserService userService;

    /**
     * Конструктор для {@code UserController}.
     *
     * @param userService сервис для управления пользователями
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Отображает страницу с данными текущего пользователя.
     *
     * @param model объект {@link Model} для передачи данных в представление
     * @return имя представления "user.html"
     */
    @GetMapping("/user")
    public String userPage(Model model) {
        // Получение информации о текущем аутентифицированном пользователе
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userService.findByUsername(currentUsername);

        model.addAttribute("user", user); // Добавление пользователя в модель
        // Отладка: вывод ролей в консоль
        System.out.println("User roles: " + user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return "user";
    }
}
