package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**
 * Класс Role представляет роль пользователя в системе.
 * <p>
 * Связан с классом {@link User} через отношение многие-ко-многим.
 * Каждая роль может быть назначена нескольким пользователям, и каждый пользователь может иметь несколько ролей.
 * </p>
 */
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название роли, например, ROLE_USER или ROLE_ADMIN.
     */
    @Column(unique = true, nullable = false)
    private String name;

    /**
     * Пользователи, связанные с данной ролью.
     */
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    // Конструкторы

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * Возвращает название роли как авторитет.
     *
     * @return название роли
     */
    @Override
    public String getAuthority() {
        return name;
    }

    // Переопределение equals и hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return name != null ? name.equals(role.name) : false;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
