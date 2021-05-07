package com.example.demo.Repositories;

import com.example.demo.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA репозиторий для заказов
 *
 * @author kanenkovaa
 * @version 0.1
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Поиск заказов по никнейму заказчика
     * @param customerUsername никнейм заказчика, по которму идёт посик заказов
     * @return Лист объектов Order
     */
    List<Order> findAllByCustomerUsername(String customerUsername);

    /**
     * Удаление заказа по его id
     * @param id id заказа
     */
    void deleteById(Long id);

    /**
     * Метод для получения списка всех заказов, отсортированных по никнейму заказчика
     * @return отсортированный список всех заказов
     */
    List<Order> findAllByOrderByCustomerUsername();

    /**
     * Поиск заказов по подстроке никнейма заказчика
     * @param username никнейм заказчика
     * @return список заказов
     */
    List<Order> findAllByCustomerUsernameContainingIgnoreCase(String username);
}
