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

    @OneToMany(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH},
            mappedBy = "order")
    @Column(name = "workers")
    private List<Employee> workers;

    public Order() {
    }

    public Order(String city, String street, String building, String apartment, String targetDate) {
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
        this.targetDate = targetDate;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<Employee> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Employee> workers) {
        if (workers != null) {
            workers.forEach(a -> {
                a.setOrder(this);
            });
        }
        this.workers = workers;
    }
}
