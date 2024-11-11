package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

/**
 * Интерфейс UserService определяет операции для управления пользователями.
 */
public interface UserService {

    User findById(Long id);

    /**
     * Находит пользователя по имени.
     *
     * @param username имя пользователя
     * @return объект {@link User} или {@code null}, если пользователь не найден
     */
    User findByUsername(String username);

    /**
     * Возвращает список всех пользователей.
     *
     * @return список пользователей
     */
    List<User> findAll();

    /**
     * Сохраняет нового пользователя.
     *
     * @param user объект пользователя
     */
    void save(User user);

    /**
     * Обновляет существующего пользователя.
     *
     * @param user объект пользователя
     */
    void update(User user);

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     */
    void delete(Long id);
}
