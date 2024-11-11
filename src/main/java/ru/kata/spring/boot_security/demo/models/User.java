package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

/**
 * Класс User представляет пользователя системы.
 * <p>
 * Реализует интерфейс {@link UserDetails}, предоставляя необходимую информацию для Spring Security.
 * Пользователь может иметь несколько ролей {@link Role}.
 * </p>
 */
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Уникальное имя пользователя.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Электронная почта пользователя.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Пароль пользователя.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Роли, назначенные пользователю.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    // Конструкторы

    public User() {
    }

    public User(String username, String email, String password, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }



    // НЕТУ МЕТОДА getUser()

    // Реализация методов UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    /**
     * Указывает, не истёк ли срок действия учетной записи пользователя.
     *
     * @return {@code true}, если учетная запись действительна
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Указывает, не заблокирована ли учетная запись пользователя.
     *
     * @return {@code true}, если учетная запись не заблокирована
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Указывает, не истек ли срок действия учетных данных пользователя.
     *
     * @return {@code true}, если учетные данные действительны
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Указывает, включена ли учетная запись пользователя.
     *
     * @return {@code true}, если учетная запись активна
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    // Переопределение equals и hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null ? id.equals(user.id) : false;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
