package com.example.demo.Validators;

import com.example.demo.Model.Truck;
import com.example.demo.Services.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Валидатор автомобиля
 *
 * @author kanenkovaa
 */
@Component
public class TruckValidator implements Validator {

    @Autowired
    private TruckService truckService;


    private Pattern pattern;
    private Matcher matcher;

    /**
     * Паттерн для проверки автомобильного номера
     */
    private static final String CARNUMBER_PATTERN = "[А-Я]\\d{3}[А-Я]{2}\\d{2,3}";

    /**
     * {@inheritDoc}
     *
     * Support
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return Truck.class.equals(aClass);
    }

    /**
     * Метод, осуществляющий проверку данных из формы добавления нового автомобиля
     * @param o объект автомобиля
     * @param errors лист для добавления ошибок
     */
    @Override
    public void validate(Object o, Errors errors) {
        Truck truck = (Truck)o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "carNumber", "обязательно к заполнению");
        if (!validateCarNumber(truck.getCarNumber()))
            errors.rejectValue("carNumber", "неправильный формат автомобильного номера");
        if (truckService.findByCarNumber(truck.getCarNumber()) != null)
            errors.rejectValue("carNumber", "номер уже занят");
    }

    /**
     * Метод проверки автомобильного номера на соответствие шаблону
     * @param carNumber строка номера автомобиля
     * @return boolean
     */
    public boolean validateCarNumber(String carNumber) {
        pattern = Pattern.compile(CARNUMBER_PATTERN);
        matcher = pattern.matcher(carNumber);
        return matcher.matches();
    }
}
