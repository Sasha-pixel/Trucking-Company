package com.example.demo.Repositories;

import com.example.demo.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * jpa репозиторий для работников компании
 *
 * @author kanenkovaa
 * @version 0.1
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}

//insert into employee8(firstname, lastname)
//    values ('Александр', 'Каненков'),
//    ('Владимир', 'Полетаев'),
//        ('Денис', 'Пономарёв'),
//        ('Василий', 'Пупкин'),
//        ('Иван', 'Иванов'),
//        ('Александр', 'Романов'),
//        ('Глеб', 'Борисов'),
//        ('Борис', 'Глебов'),
//        ('Антон', 'Камнев'),
//        ('Сергей', 'Меняев');
