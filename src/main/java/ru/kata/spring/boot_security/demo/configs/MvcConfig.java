package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Класс MvcConfig настраивает контроллеры представлений.
 * <p>
 * Определяет простые перенаправления URL на соответствующие представления.
 * </p>
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * Добавляет контроллеры представлений для статических страниц.
     *
     * @param registry объект {@link ViewControllerRegistry} для регистрации контроллеров
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Перенаправление URL "/user" на представление "user.html"
        registry.addViewController("/user").setViewName("user");

        // Перенаправление URL "/admin" на представление "admin.html"
        registry.addViewController("/admin").setViewName("admin/admin");

        // Перенаправление корневого URL "/" на представление "index.html"
        registry.addViewController("/").setViewName("index");

        // Перенаправление URL "/index" на представление "index.html"
        registry.addViewController("/index").setViewName("index");
    }
}
