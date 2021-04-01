package com.example.demo.Validators;

import com.example.demo.Model.User;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Валидатор для проверки данных из форме для регистрации нового пользователя
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

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
        if (userService.findByUsername(user.getUsername()) != null)
            errors.rejectValue("username", "пользователь с таким ником уже существует");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "обязательно к заполнению");
        if (!userService.validateEmail(user.getEmail()))
            errors.rejectValue("email", "неправильный формат эл. почты");
        if (userService.findByEmail(user.getEmail()) != null)
            errors.rejectValue("email", "пользователь с такой эл. почтой уже сущетсвует");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "обязательно к заполнению");
        if (!userService.validatePhoneNumber(user.getPhoneNumber()))
            errors.rejectValue("phoneNumber", "неправильный формат номера телефона");
        if (userService.findByPhoneNumber(user.getPhoneNumber()) != null)
            errors.rejectValue("phoneNumber", "пользователь с таким номером телефона уже существует");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "обязательно к заполнению");
        if (user.getPassword().length() < 6)
            errors.rejectValue("password", "пароль не должен быть короче 6 символов");
    }
}
