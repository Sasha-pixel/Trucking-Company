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

    @Column(name = "address_from")
    private String addressFrom;

    @Column(name = "address_to")
    private String addressTo;

    @Column(name = "distance")
    private String distance;

    @Column(name = "duration")
    private String duration;

    @Column(name = "target_date")
    private String targetDate;

    @Column(name = "targetTime")
    private String targetTime;

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

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    @JoinColumn(name = "truck_id")
    private Truck truck;

    @Column(name = "price")
    private int price;

    /**
     * пустой конструктор
     */
    public Order() {
    }

    /**
     * параметризованный конструктор
     * @param customerUsername никнейм заказчика
     * @param addressFrom адрес пункта отправки
     * @param addressTo адрес пункта назначения
     * @param targetDate дата выполнения заказа
     * @param targetTime время выполнения заказа
     * @param creationDate дата оформления заказа
     * @param distance дистанция перевозки груза
     * @param duration длительность перевозки груза
     * @param workers список сотрудников
     * @param truck автомобиль
     * @param price примерная стоимость выполнения заказа
     */
    public Order(String customerUsername,
                 String addressFrom,
                 String addressTo,
                 String targetDate,
                 String targetTime,
                 Date creationDate,
                 String distance,
                 String duration,
                 List<Employee> workers,
                 Truck truck,
                 int price) {
        this.customerUsername = customerUsername;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.targetDate = targetDate;
        this.targetTime = targetTime;
        this.creationDate = creationDate;
        this.distance = distance;
        this.duration = duration;
        this.workers = workers;
        this.truck = truck;
        this.price = price;
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
     * @param id id заказа
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
     * @param customerUsername никнейм заказчика
     */
    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    /**
     * получение адреса пункта отправки
     * @return адрес пункта отправки
     */
    public String getAddressFrom() {
        return addressFrom;
    }

    /**
     * установка адреса пункта отправки
     * @param addressFrom адрес пункта отправки
     */
    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    /**
     * получение адреса пункта назначения
     * @return дрес пункта назначения
     */
    public String getAddressTo() {
        return addressTo;
    }

    /**
     * установка адреса пункта назначения
     * @param addressTo
     */
    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
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
     * @param targetDate дата выполнения заказа
     */
    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    /**
     * получение времени выполнения заказа
     * @return время выполнения заказа
     */
    public String getTargetTime() {
        return targetTime;
    }

    /**
     * установка времени выполнения заказа
     * @param targetTime время выполнения заказа
     */
    public void setTargetTime(String targetTime) {
        this.targetTime = targetTime;
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
     * @param creationDate дата создания заказа
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * получение длительности перевозки
     * @return длительность перевозки
     */
    public String getDistance() {
        return distance;
    }

    /**
     * установка длительности перевозки
     * @param distance длительность перевозки
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /**
     * получение продолжительности перевозки
     * @return продолжительность перевозки
     */
    public String getDuration() {
        return duration;
    }

    /**
     * установка продолжительности перевозки
     * @param duration продолжительность перевозки
     */
    public void setDuration(String duration) {
        this.duration = duration;
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
     * @param workers лист сотрудников
     */
    public void setWorkers(List<Employee> workers) {
//        if (workers != null) {
//            workers.forEach(a -> {
//                a.setOrders(this);
//            });
//        }
        this.workers = workers;
    }

    /**
     * получение грузовика
     * @return truck - грузовик
     */
    public Truck getTruck() {
        return truck;
    }

    /**
     * установка грузовика на заказ
     * @param truck рузовик
     */
    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    /**
     * получение примерной стоимости выполнения заказа
     * @return стоимость выполнения заказа
     */
    public int getPrice() {
        return price;
    }

    /**
     * установка примерной стоимости выполнения заказа
     * @param price примерная стоимость выполнения заказа
     */
    public void setPrice(int price) {
        this.price = price;
    }
}
