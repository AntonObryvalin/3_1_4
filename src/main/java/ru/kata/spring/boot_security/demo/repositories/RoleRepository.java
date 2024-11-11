package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

/**
 * Репозиторий для работы с сущностью {@link Role}.
 * <p>
 * Предоставляет методы для выполнения CRUD операций и поиска ролей по имени.
 * </p>
 */

// Вообще ХЗ нужен ли этот класс
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Находит роль по имени.
     *
     * @param name имя роли
     * @return объект {@link Role} или {@code null}, если роль не найдена
     */
    Role findByName(String name);
}
