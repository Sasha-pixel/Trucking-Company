package com.example.demo.Services;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Order;
import com.example.demo.Model.Truck;
import com.example.demo.Repositories.EmployeeRepository;
import com.example.demo.Validators.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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

    @Autowired
    private EmployeeValidator employeeValidator;

    /**
     * получение всех работников из таблицы
     * @return лист работников
     */
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    /**
     * добавление нового сотрудника
     * @param employee объект сотрудника
     */
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    /**
     * Метод поиска сотрудника по имени и фамилии
     * @param name имя и фамилия
     * @return объект сотрудника
     */
    public Employee findByName(String name) {
        return employeeRepository.findByName(name);
    }

    /**
     * получение списка сотрудников по подстроке их имени или фамилии
     * @param employee подстрока имени или фамилии
     * @return список сотрудников
     */
    public List<Employee> searchEmployee(String employee) {
        return employeeRepository.findByNameContainingIgnoreCase(employee);
    }

    /**
     * получение заказов всех работников
     * @return список заказов
     */
    public List<Order> getAllEmployeesOrders() {
        List<Order> orders = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            orders.addAll(employee.getOrders());
        }
        return orders;
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

    /**
     * Проверка наличия ошибок после валидации данных из формы добавления автомобиля
     * @param employee объект сотрудника
     * @param bindingResult лист для добавления ошибок
     * @param model модель веб-страницы
     * @return
     */
    public boolean validateEmployee(Employee employee, BindingResult bindingResult, Model model) {
        employeeValidator.validate(employee, bindingResult);
        if (bindingResult.hasErrors()) {
            for (Object object : bindingResult.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError)object;
                    model.addAttribute(fieldError.getField(), fieldError.getCode());
                }
            }

            return true;
        }
        else
            return false;
    }
}
