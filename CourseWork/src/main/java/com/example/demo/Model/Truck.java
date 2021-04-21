package com.example.demo.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Модель грузовика
 *
 * @author kanenkovaa
 * @version 0.2
 */
@Entity
@Table(schema = "public", name = "truck8")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "car_number")
    private String carNumber;

    @OneToMany(mappedBy = "truck",
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    private List<Order> orders;

    /**
     * пустой конструктор
     */
    public Truck() {
    }

    /**
     * параметризованный конструктор
     * @param description характеристика грузовика
     * @param carNumber автомобильный номер
     * @param orders список заказов
     */
    public Truck(String description, String carNumber, List<Order> orders) {
        this.description = description;
        this.carNumber = carNumber;
        this.orders = orders;
    }

    /**
     * получение характеристики грузовика
     * @return description - характеристика грузовика
     */
    public String getDescription() {
        return description;
    }

    /**
     * установка характеристики грузовика
     * @param description характеристика грузовика
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * получение автомобильного номера
     * @return carNumber - автомобильный номер
     */
    public String getCarNumber() {
        return carNumber;
    }

    /**
     * установка автомобильного номера
     * @param carNumber автомобильный номер
     */
    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    /**
     * получение списка заказов
     * @return orders - список заказов
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * установка списка заказов
     * @param orders список заказов
     */
    public void setOrders(List<Order> orders) {
        if (orders != null) {
            orders.forEach(a->{
                a.setTruck(this);
            });
        }
        this.orders = orders;
    }
}
