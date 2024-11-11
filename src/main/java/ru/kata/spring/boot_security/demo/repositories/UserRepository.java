package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

/**
 * Репозиторий для работы с сущностью {@link User}.
 * <p>
 * Предоставляет методы для выполнения CRUD операций и поиска пользователей по имени.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по имени пользователя.
     *
     * @param username имя пользователя
     * @return объект {@link User} или {@code null}, если пользователь не найден
     */
    //!!!!!!!
    // Должно быть Optional<Users> findByUsername(String username);
    User findByUsername(String username);

    /**
     * Находит пользователя по электронной почте.
     *
     * @param email электронная почта пользователя
     * @return объект {@link User} или {@code null}, если пользователь не найден
     */
    User findByEmail(String email);
}
