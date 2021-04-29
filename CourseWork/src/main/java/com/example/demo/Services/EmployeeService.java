package com.example.demo.Services;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Order;
import com.example.demo.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис, осуществляющий работу с таблицей работников компании
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * получение всех работников из таблицы
     * @return лист работников
     */
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    /**
     * Установка работников на заказ с учётом занятости
     *
     * @param orderForm объект заказа
     * @param workers список всех работников, из которого будут отбираться подходящие
     * @return
     */
    public List<Employee> setWorkersToOrder(Order orderForm, List<Employee> workers) {
        List<Employee> workersBuf = new ArrayList<>();
        boolean flag;
        for (Employee worker : workers) {
            flag = true;
            if (worker.getOrders().isEmpty())
                workersBuf.add(worker);
            else {
                for (Order order : worker.getOrders()) {
                    if (order.getTargetDate().equals(orderForm.getTargetDate()))
                        flag = false;
                }
                if (flag)
                    workersBuf.add(worker);
            }
        }
        return workersBuf;
    }
}
