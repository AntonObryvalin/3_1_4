package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * Контроллер AuthController отвечает за отображение страниц логина и регистрации,
 * а также за обработку запросов на регистрацию новых пользователей.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    /**
     * Конструктор для {@code AuthController}.
     *
     * @param userService     сервис для управления пользователями
     * @param passwordEncoder бин для кодирования паролей
     */
    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    /**
     * Отображает страницу логина.
     *
     * @return имя представления "auth/login.html"
     */
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    /**
     * Отображает страницу регистрации.
     *
     * @param model объект {@link Model} для передачи данных в представление
     * @return имя представления "auth/registration.html"
     */
    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("user", new User()); // Создание нового объекта пользователя для формы
        return "auth/registration";
    }

    /**
     * Обрабатывает POST-запрос на регистрацию нового пользователя.
     *
     * @param user          объект {@link User}, полученный из формы регистрации
     * @param bindingResult объект {@link BindingResult}, содержащий результаты валидации
     * @return перенаправление на страницу логина или возврат на страницу регистрации при ошибках
     */
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult) {

        // Валидация данных пользователя при регистрации.
        // Проверяет, существует ли пользователь с таким именем или электронной почтой.
        if (userService.findByUsername(user.getUsername()) != null) {
            bindingResult.rejectValue("username", "", "Пользователь с таким именем уже существует");
        }

//        if (userService.findByEmail(user.getEmail()) != null) {
//            bindingResult.rejectValue("email", "", "Пользователь с такой электронной почтой уже существует");
//        }

        // Проверка, были ли обнаружены ошибки валидации.
        // Если ошибки есть, возвращаемся на страницу регистрации для их исправления.
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        // Назначение роли ROLE_USER
        Role userRole = roleRepository.findByName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        // Хеширование пароля перед сохранением
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Если валидация прошла успешно, сохраняем нового пользователя в базе данных.
        userService.save(user);

        // После успешной регистрации перенаправляем пользователя на страницу входа для аутентификации.
        return "redirect:/auth/login";
    }
}
