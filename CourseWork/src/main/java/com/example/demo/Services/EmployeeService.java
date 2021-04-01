package com.example.demo.Services;

import com.example.demo.Model.Employee;
import com.example.demo.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
