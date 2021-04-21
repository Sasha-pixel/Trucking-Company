package com.example.demo.Services;

import com.example.demo.Model.User;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Сервис, осуществляющий связь контроллеров и таблицы с пользователями
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    private Pattern pattern;
    private Matcher matcher;

    /**
     * Паттерны для проверки номеров телефона и эл.адресов
     */
    //private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@ [A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String EMAIL_PATTERN = "[a-zA-Z0-9[!#$%&'()*+,/\\-_\\.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\"\\.]]+";
    private static final String PHONE_PATTERN = "^[0-9.()-]{10,25}$";

    /**
     * Кодирование пароля
     *
     * @param password a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean matches(String rawPassword, String oldPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, oldPassword);
    }

    /**
     * Сохранение нового пользователя или обновление уже существующего в таблице
     *
     * @param user a {@link com.example.demo.Model.User} object.
     */
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Поиск по никнейму пользователя
     *
     * @param username a {@link java.lang.String} object.
     * @return a {@link com.example.demo.Model.User} object.
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Поиск пользователя по адресу эл.почты
     *
     * @param email a {@link java.lang.String} object.
     * @return a {@link com.example.demo.Model.User} object.
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Посик пользователя по номеру телефона
     *
     * @param phoneNumber a {@link java.lang.String} object.
     * @return a {@link com.example.demo.Model.User} object.
     */
    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    /**
     * Проверка адреса эл.почты по паттерну
     *
     * @param email a {@link java.lang.String} object.
     * @return a boolean.
     */
    public boolean validateEmail(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Проверка номера телефона по паттерну
     *
     * @param phoneNumber a {@link java.lang.String} object.
     * @return a boolean.
     */
    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("8") || phoneNumber.startsWith("+7")) {
            if (phoneNumber.startsWith("8")) {
                pattern = Pattern.compile(PHONE_PATTERN);
                matcher = pattern.matcher(phoneNumber.substring(1));
                return matcher.matches();
            }
            else if (phoneNumber.startsWith("+7")) {
                pattern = Pattern.compile(PHONE_PATTERN);
                matcher = pattern.matcher(phoneNumber.substring(2));
                return matcher.matches();
            }
        }
        return false;
    }

    /**
     * Создание и сохранение cookie файлов
     *
     * @param httpServletResponse a {@link javax.servlet.http.HttpServletResponse} object.
     */
    public void createCookie(HttpServletResponse httpServletResponse) {
        User user = findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Cookie usernameCookie = new Cookie("username", user.getUsername());
        Cookie passwordCookie = new Cookie("password", user.getPassword());
        httpServletResponse.addCookie(usernameCookie);
        httpServletResponse.addCookie(passwordCookie);
    }
}
