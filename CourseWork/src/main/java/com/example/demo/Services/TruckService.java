package com.example.demo.Services;

import com.example.demo.Model.Order;
import com.example.demo.Model.Truck;
import com.example.demo.Repositories.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

/**
 * Сервис, осуществляющий работу с таблицей грузовиков
 *
 * @author kanenkovaa
 * @version 0.2
 */
@Service
public class TruckService {

    @Autowired
    private TruckRepository truckRepository;

    /**
     * Поиск грузовиков по характеристике
     *
     * @param description характеристика грузовика
     * @return description - характеристика грузовика
     */
    public List<Truck> findAllByDescription(String description) {
        return truckRepository.findAllByDescription(description);
    }

    /**
     * установка автомобиля на заказ
     * @param orderForm объект заказа
     * @param trucks лист всех автомобилей
     * @return
     */
    public Truck setTruckToOrder(Order orderForm, List<Truck> trucks) {
        boolean flag;
        for (Truck truck : trucks) {
            flag = true;
            if (truck.getOrders().isEmpty()) {
                return truck;
            }
            else {
                for (Order order : truck.getOrders()) {
                    if (order.getTargetDate().equals(orderForm.getTargetDate()))
                        flag = false;
                }
                if (flag) {
                    return truck;
                }
            }
        }
        return null;
    }
}
