package com.example.demo.Repositories;

import com.example.demo.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * jpa репозиторий для работников компании
 *
 * @author kanenkovaa
 * @version 0.1
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
