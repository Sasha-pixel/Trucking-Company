package com.example.demo.Services;

import com.example.demo.Model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Класс, отвечающий за автоудаление заказов
 */
@Service
public class ScheduledDeletion {

    @Autowired
    private OrderService orderService;

    /**
     * Метод, удаляющий заказы, дата выполнения которых прошла
     * @throws ParseException
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOrders() throws ParseException {
        List<Order> orders = orderService.findAll();
        Date currentDate = new Date();
        for (Order order : orders) {
            Date targetDate = new SimpleDateFormat("yyyy-MM-dd").parse(order.getTargetDate());
            System.out.println(targetDate);
            if (targetDate.before(currentDate))
                orderService.delete(order.getId());
        }
    }
}
