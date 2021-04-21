package com.example.demo.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Модель заказа
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Entity
@Table(schema = "public", name = "order8")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_username")
    private String customerUsername;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "building")
    private String building;

    @Column(name = "apartment")
    private String apartment;

    @Column(name = "target_date")
    private String targetDate;

    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    @JoinTable(name = "employees_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> workers;

    /**
     * пустой конструктор
     */
    public Order() {
    }

    /**
     * параметризованный конструктор v1
     * @param city - город
     * @param street - улица
     * @param building - строение
     * @param apartment - квартира
     * @param targetDate - дата выполнения заказа
     */
    public Order(String city, String street, String building, String apartment, String targetDate) {
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
        this.targetDate = targetDate;
    }

    /**
     * параметризованный конструктор v2
     * @param id - id
     * @param customerUsername - никнейм заказчика
     * @param city - город
     * @param street - улица
     * @param building - строение
     * @param apartment - квартира
     * @param targetDate - дата выполнения заказа
     * @param creationDate - дата создания заказа
     * @param workers - сотрудники
     */
    public Order(Long id,
                 String customerUsername,
                 String city,
                 String street,
                 String building,
                 String apartment,
                 String targetDate,
                 Date creationDate,
                 List<Employee> workers) {
        this.id = id;
        this.customerUsername = customerUsername;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
        this.targetDate = targetDate;
        this.creationDate = creationDate;
        this.workers = workers;
    }

    /**
     *  получение id
     * @return id заказа
     */
    public Long getId() {
        return id;
    }

    /**
     * установка id
     * @param id заказа
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * получение никнейма заказчика
     * @return customerUsername - никнейм заказчика
     */
    public String getCustomerUsername() {
        return customerUsername;
    }

    /**
     * установка никнейма заказчика
     * @param customerUsername - никнейм заказчика
     */
    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    /**
     * получение города
     * @return city - город
     */
    public String getCity() {
        return city;
    }

    /**
     * установка города
     * @param city - город
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * получение улицы
     * @return street - улица
     */
    public String getStreet() {
        return street;
    }

    /**
     * установка улицы
     * @param street - улица
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * олучение строеня
     * @return building - строение
     */
    public String getBuilding() {
        return building;
    }

    /**
     * установка строения
     * @param building - строение
     */
    public void setBuilding(String building) {
        this.building = building;
    }

    /**
     * получение квартиры
     * @return apartment - квартира
     */
    public String getApartment() {
        return apartment;
    }

    /**
     * установка квартиры
     * @param apartment - квартира
     */
    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    /**
     * получение даты выполнения заказа
     * @return targetDate - дата выполнения заказа
     */
    public String getTargetDate() {
        return targetDate;
    }

    /**
     * установка даты выполнения заказа
     * @param targetDate - дата выполнения заказа
     */
    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    /**
     * получение даты создания заказа
     * @return creationDate - дата создания заказа
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * установка даты создания заказа
     * @param creationDate - дата создания заказа
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * получение списка сотрудников
     * @return workers - лист сотрудников
     */
    public List<Employee> getWorkers() {
        return workers;
    }

    /**
     * установка сотрудников на заказ
     * @param workers - лист сотрудников
     */
    public void setWorkers(List<Employee> workers) {
//        if (workers != null) {
//            workers.forEach(a -> {
//                a.setOrders(this);
//            });
//        }
        this.workers = workers;
    }
}
