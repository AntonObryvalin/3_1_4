package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Реализация интерфейса {@link UserService} и {@link UserDetailsService}.
 * <p>
 * Предоставляет бизнес-логику для управления пользователями и интеграцию с Spring Security.
 * </p>
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Конструктор для {@code UserServiceImpl}.
     *
     * @param userRepository репозиторий для работы с пользователями
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Загружает пользователя по имени для аутентификации.
     *
     * @param username имя пользователя
     * @return объект {@link UserDetails}
     * @throws UsernameNotFoundException если пользователь не найден
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return user;
    }

    /**
     * Находит пользователя по имени.
     *
     * @param username имя пользователя
     * @return объект {@link User} или {@code null}, если пользователь не найден
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return список пользователей
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Сохраняет нового пользователя.
     *
     * @param user объект пользователя
     */
    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * Обновляет существующего пользователя.
     *
     * @param user объект пользователя
     */
    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     */
    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
