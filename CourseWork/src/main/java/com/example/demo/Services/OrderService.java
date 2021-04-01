package com.example.demo.Services;

import com.example.demo.Model.Order;
import com.example.demo.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис, осуществляющий связь контроллера заказов с таблицей заказов
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * метод сохранения нового заказа в бд
     *
     * @param order объект заказа
     */
    public void save(Order order) {
        orderRepository.save(order);
    }

    /**
     * получение данных о всех заказах пользователя
     *
     * @param customerUsername никней пользователя
     * @return лист заказов
     */
    public List<Order> findAllByCustomerUsername(String customerUsername) {
        return orderRepository.findAllByCustomerUsername(customerUsername);
    }
}
