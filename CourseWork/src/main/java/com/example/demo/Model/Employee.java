package com.example.demo.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Модель работника компании
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Entity
@Table(schema = "public", name = "employee8")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH},
            mappedBy = "workers")
    private List<Order> orders;

    /**
     * пустой конструктор
     */
    public Employee() {
    }

    /**
     * параметризованный конструктор
     * @param name имя и фамилия
     * @param orders заказ
     */
    public Employee(String name, List<Order> orders) {
        this.name = name;
        this.orders = orders;
    }

    /**
     * получение номера сотрудника
     * @return номер сотрудника
     */
    public Long getId() {
        return id;
    }

    /**
     * установка номера сотрудника
     * @param id номер сотрудника
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * получение имени и фамилии
     * @return firstName
     */
    public String getName() {
        return name;
    }

    /**
     * установка имени
     * @param name имя и фамилия
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * получение заказа
     * @return order - заказ
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * установка заказа
     * @param orders заказ
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    /**
     * метод для вывода информации о сотруднике
     * @return имя и фамилию сотрудников
     */
    @Override
    public String toString() {
        return getName();
    }
}
