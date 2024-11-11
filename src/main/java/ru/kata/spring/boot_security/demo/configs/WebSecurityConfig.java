package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Класс WebSecurityConfig настраивает безопасность приложения.
 * <p>
 * Определяет доступ к URL, настройки формы логина и интеграцию с {@link UserServiceImpl}.
 * </p>
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;
    private final UserServiceImpl userService;

    /**
     * Конструктор для {@code WebSecurityConfig}.
     *
     * @param successUserHandler хэндлер для обработки успешной аутентификации
     * @param userService        сервис для управления пользователями
     */
    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserServiceImpl userService) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
    }

    /**
     * Настраивает HTTP безопасность, определяя доступ к URL и настройки формы логина.
     *
     * @param http объект {@link HttpSecurity} для настройки безопасности
     * @throws Exception если происходит ошибка конфигурации
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Отключение защиты от CSRF (для упрощения)
                .csrf().disable()

                // Настройка авторизации запросов
                .authorizeRequests()
                // Доступ к корневому URL и странице index разрешён всем
                .antMatchers("/", "/index", "/auth/login", "/auth/registration").permitAll()

                // Доступ к URL /admin/** только для пользователей с ролью ADMIN
                .antMatchers("/admin/**").hasRole("ADMIN")

                // Доступ к URL /user/** для пользователей с ролями USER или ADMIN
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")

                // Любые другие запросы требуют аутентификации
                .anyRequest().authenticated()
                .and()




                // Настройка формы логина
                .formLogin()
                .loginPage("/auth/login") // Путь к странице логина
                .loginProcessingUrl("/process_login") // Путь для обработки логина
                .successHandler(successUserHandler) // Хэндлер для успешного логина
//                .defaultSuccessUrl("/user", true)
                .permitAll()
                .and()

                // Настройка выхода из системы (logout)
                .logout()
                .logoutUrl("/logout") // Путь для выхода из системы
                .logoutSuccessUrl("/auth/login") // Путь после успешного выхода
                .permitAll();
    }

    /**
     * Настраивает менеджер аутентификации, используя {@link UserServiceImpl}.
     *
     * @param auth объект {@link AuthenticationManagerBuilder} для настройки аутентификации
     * @throws Exception если происходит ошибка конфигурации
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)// Настройка userDetailsService для аутентификации
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
