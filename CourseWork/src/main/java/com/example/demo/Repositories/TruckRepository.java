package com.example.demo.Repositories;

import com.example.demo.Model.Truck;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * jpa репозиторий для грузовиков
 *
 * @author kanenkovaa
 * @version 0.2
 */
public interface TruckRepository extends CrudRepository<Truck, Long> {

    /**
     * Поиск грузовиков по характеристике
     * @param description характеристика грузовика
     * @return лист грузовиков с указанной характеристикой
     */
    List<Truck> findAllByDescription(String description);

    /**
     * Поиск грузовиков по номеру
     * @param carNumber автомобильный номер
     * @return грузовик с указанным номером
     */
    Truck findByCarNumber(String carNumber);
    
    void deleteAllByDescription(String description);

    void deleteAllByCarNumber(String carNumber);
}
