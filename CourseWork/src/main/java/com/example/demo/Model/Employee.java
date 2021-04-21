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

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String SecondName;

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
     * @param firstName - имя
     * @param secondName - фамилия
     * @param order - заказ
     */
    public Employee(String firstName, String secondName, List<Order> order) {
        this.firstName = firstName;
        SecondName = secondName;
        this.orders = orders;
    }

    /**
     * получение имени
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * установка имени
     * @param firstName - имя
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * получение фамилии
     * @return secondName - фамилия
     */
    public String getSecondName() {
        return SecondName;
    }

    /**
     * установка фамилии
     * @param secondName - фамилия
     */
    public void setSecondName(String secondName) {
        SecondName = secondName;
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
     * @param orders - заказ
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
        return getFirstName() + " " + getSecondName();
    }
}
