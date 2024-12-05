//package ru.kata.spring.boot_security.demo.controllers;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_security.demo.models.Role;
//import ru.kata.spring.boot_security.demo.models.User;
//import ru.kata.spring.boot_security.demo.services.RoleService;
//import ru.kata.spring.boot_security.demo.services.UserService;
//
//import java.security.Principal;
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
//    private final RoleService roleService;
//
//    public AdminController(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//    @ModelAttribute
//    public void addCurrentUserInfo(Model model, Principal principal) {
//        if (principal != null) {
//            User currentUser = userService.findByUsername(principal.getName());
//            if (currentUser != null) {
//                model.addAttribute("adminEmail", currentUser.getEmail());
//
//                String roles = currentUser.getRoles()
//                        .stream()
//                        .map(Role::getName)
//                        .collect(Collectors.joining(" "));
//                model.addAttribute("adminRoles", roles);
//            } else {
//                model.addAttribute("adminEmail", "Unknown");
//                model.addAttribute("adminRoles", "None");
//            }
//        } else {
//            model.addAttribute("adminEmail", "Guest");
//            model.addAttribute("adminRoles", "None");
//        }
//    }
//
//    @GetMapping
//    public String allUsers(Model model) {
//        model.addAttribute("users", userService.findAll());
//        model.addAttribute("newUser", new User());
//
//        model.addAttribute("allRoles", roleService.findAll()); // Все роли
//
//        return "admin/admin";
//    }
//
//    @GetMapping("/new")
//    public String newUserForm(Model model) {
//        model.addAttribute("allRoles", roleService.findAll());
//        return "admin/edit_user";
//    }
//
//    @PostMapping
//    public String addUser(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roles) {
//        Set<Role> userRoles = new HashSet<>(roleService.findAllById(roles));
//        user.setRoles(userRoles);
//        userService.save(user);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String editUser(@PathVariable("id") Long id, Model model) {
//        User user = userService.findById(id);
//        if (user == null) {
//            return "redirect:/admin";
//        }
//        model.addAttribute("user", user);
//        model.addAttribute("allRoles", roleService.findAll());
//        return "admin/edit_user";
//    }
//
//    @PostMapping("/{id}/update")
//    public String updateUser(
//            @PathVariable("id") Long id,
//            @ModelAttribute("user") User user,
//            @RequestParam("roles") List<Long> roles,
//            Model model) {
//
//        User existingUser = userService.findById(id);
//        if (existingUser == null) {
//            return "redirect:/admin";
//        }
//
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
//        existingUser.setRoles(new HashSet<>(roleService.findAllById(roles)));
//
//        userService.update(existingUser, user.getPassword());
//        return "redirect:/admin";
//    }
//
//    @PostMapping("/{id}/delete")
//    public String deleteUser(@PathVariable("id") Long id) {
//        userService.delete(id);
//        return "redirect:/admin";
//    }
//
//}
//
