package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Класс SuccessUserHandler обрабатывает успешную аутентификацию пользователя.
 * <p>
 * В зависимости от роли пользователя перенаправляет его на соответствующую страницу.
 * </p>
 */
@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    /**
     * Метод вызывается после успешной аутентификации.
     *
     * @param request        HTTP-запрос
     * @param response       HTTP-ответ
     * @param authentication объект {@link Authentication}, содержащий информацию о пользователе
     * @throws IOException      если происходит ошибка ввода-вывода
     * @throws ServletException если происходит ошибка сервлета
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Получение ролей пользователя
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // Перенаправление пользователя в зависимости от его роли
        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin");
        } else if (roles.contains("ROLE_USER")) {
            response.sendRedirect("/user");
        } else {
            response.sendRedirect("/"); // Если роль не распознана, перенаправляем на корень
        }
    }
}
