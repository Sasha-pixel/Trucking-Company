package com.example.demo.Validators;

import com.example.demo.Model.User;
import com.example.demo.Services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Валидатор для проверки данных из форме для регистрации нового пользователя
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Component
public class AuthorizationValidator implements Validator {

    @Autowired
    private AuthorizationService authorizationService;

    private Pattern pattern;
    private Matcher matcher;

    /**
     * Паттерны для проверки номеров телефона и эл.адресов
     */
    private static final String EMAIL_PATTERN = "[a-zA-Z0-9[!#$%&'()*+,/\\-_\\.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\"\\.]]+";
    private static final String PHONE_PATTERN = "^[0-9.()-]{10}";

    /**
     * {@inheritDoc}
     *
     * Support
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    /**
     * Метод, осуществляющий проверку данных из формы регистрации пользователя
     * @param o объект пользователя
     * @param errors лист для добавления ошибок
     */
    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "обязательно к заполнению");
        if (user.getUsername().length() < 4 || user.getUsername().length() >= 20)
            errors.rejectValue("username", "ник должен быть от 4 до 20 символов");
        if (authorizationService.findByUsername(user.getUsername()) != null)
            errors.rejectValue("username", "пользователь с таким ником уже существует");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "обязательно к заполнению");
        if (!validateEmail(user.getEmail()))
            errors.rejectValue("email", "неправильный формат эл. почты");
        if (authorizationService.findByEmail(user.getEmail()) != null)
            errors.rejectValue("email", "пользователь с такой эл. почтой уже сущетсвует");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "обязательно к заполнению");
        if (!validatePhoneNumber(user.getPhoneNumber()))
            errors.rejectValue("phoneNumber", "неправильный формат номера телефона");
        if (authorizationService.findByPhoneNumber(user.getPhoneNumber()) != null)
            errors.rejectValue("phoneNumber", "пользователь с таким номером телефона уже существует");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "обязательно к заполнению");
        if (user.getPassword().length() < 6)
            errors.rejectValue("password", "пароль не должен быть короче 6 символов");
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
}
