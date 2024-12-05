package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUsername(String username) {
        return entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

//    @Override
//    @Transactional
//    public void update(User user) {
//        User managedUser = entityManager.find(User.class, user.getId());
//        if (managedUser == null) {
//            throw new IllegalArgumentException("Пользователь не найден");
//        }
//
//        managedUser.setPassword(user.getPassword());
//        managedUser.setRoles(user.getRoles());
//    }

    @Override
    @Transactional
    public void update(User user) {
        User managedUser = entityManager.find(User.class, user.getId());
        if (managedUser == null) {
            throw new IllegalArgumentException("Пользователь не найден");
        }

        // Обновляем поля
        managedUser.setUsername(user.getUsername());
        managedUser.setEmail(user.getEmail());
        managedUser.setPassword(user.getPassword());
        managedUser.setRoles(user.getRoles());

    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = findById(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
}
