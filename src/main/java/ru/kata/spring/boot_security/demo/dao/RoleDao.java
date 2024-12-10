package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;
import java.util.List;
import java.util.Set;

public interface RoleDao {
    Role findById(Long id);
    Role findByName(String name);
    List<Role> findAll();
    Set<Role> findAllById(List<Long> ids);
    void save(Role role);
}
