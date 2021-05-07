package com.example.demo.Validators;

import com.example.demo.Model.Employee;
import com.example.demo.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Валидатор сотрудника
 *
 * @author kanenkovaa
 */
@Component
public class EmployeeValidator implements Validator {

    @Autowired
    private EmployeeService employeeService;

    private Pattern pattern;
    private Matcher matcher;

    /**
     * Паттерн для проверки имени и фамилии
     */
    private static final String NAME_PATTERN = "^[а-яА-Я_]+( [а-яА-Я_]+)*$";

    /**
     * {@inheritDoc}
     *
     * Support
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return Employee.class.equals(aClass);
    }

    /**
     * Метод, осуществляющий проверку данных из формы добавления нового автомобиля
     * @param o объект сотрудника
     * @param errors лист для добавления ошибок
     */
    @Override
    public void validate(Object o, Errors errors) {
        Employee employee = (Employee) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "обязательно к заполнению");
        if (!validateName(employee.getName()))
            errors.rejectValue("name", "неправильный формат имени или фамилии");
    }

    /**
     * Метод проверки имени и фамилии на соответствие шаблону
     * @param name имя и фамилия нового сотрудника
     * @return boolean
     */
    public boolean validateName(String name) {
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
