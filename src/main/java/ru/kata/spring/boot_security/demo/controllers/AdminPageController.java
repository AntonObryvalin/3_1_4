package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import java.security.Principal;
import java.util.stream.Collectors;

@Controller
public class AdminPageController {

    private final UserService userService;

    public AdminPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin/admin";
    }

    @ModelAttribute
    public void addCurrentUserInfo(Model model, Principal principal) {
        if (principal != null) {
            User currentUser = userService.findByUsername(principal.getName());
            if (currentUser != null) {
                model.addAttribute("adminEmail", currentUser.getEmail());

                String roles = currentUser.getRoles()
                        .stream()
                        .map(role -> role.getName().replace("ROLE_", ""))
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
}
