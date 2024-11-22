
package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.models.Role;

import java.security.Principal;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void addCurrentUserInfo(Model model, Principal principal) {
        if (principal != null) {
            User currentUser = userService.findByUsername(principal.getName());
            if (currentUser != null) {
                model.addAttribute("adminEmail", currentUser.getEmail());

                // Собираем роли в строку, разделенную пробелами
                String roles = currentUser.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.joining(" "));
                model.addAttribute("adminRoles", roles);
            } else {
                model.addAttribute("adminEmail", "Unknown");
                model.addAttribute("adminRoles", "None");
            }
        } else {
            model.addAttribute("adminEmail", "Guest");
            model.addAttribute("adminRoles", "None");
        }
    }

    @GetMapping("/user")
    public String userPage(Model model, Principal principal) {

        if (principal != null) {
            User user = userService.findByUsername(principal.getName());

            if (user != null) {
                model.addAttribute("user", user);
                System.out.println("User roles: " + user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
                return "user";
            }
        }

        return "redirect:/auth/login";
    }
}
