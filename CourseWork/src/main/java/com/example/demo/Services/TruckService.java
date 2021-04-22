package com.example.demo.Services;

import com.example.demo.Model.Truck;
import com.example.demo.Repositories.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
