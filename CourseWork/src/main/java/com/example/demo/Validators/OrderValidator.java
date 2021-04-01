package com.example.demo.Validators;

import com.example.demo.Model.Order;
import com.example.demo.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Валидатор для проверки данных из формы оформления нового заказа
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Component
public class OrderValidator implements Validator {

    @Autowired
    private OrderService orderService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Order.class.equals(aClass);
    }

    /**
     * Метод, осуществляющий проверку данных из формы добавления нового заказа
     * @param o объект пользователя
     * @param errors лист для добавления ошибок
     */
    @Override
    public void validate(Object o, Errors errors) {
        Order order = (Order) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "обязательно к заполнению");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "street", "обязательно к заполнению");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "building", "обязательно к заполнению");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "targetDate", "обязательно к заполнению");
    }
}
