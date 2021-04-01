package com.example.demo.Model;

import javax.persistence.*;

/**
 * сущность работников компании
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

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "order_id")
    private Order order;

    public Employee() {
    }

    public Employee(String firstName, String secondName, Order order) {
        this.firstName = firstName;
        SecondName = secondName;
        this.order = order;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return SecondName;
    }

    public void setSecondName(String secondName) {
        SecondName = secondName;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getSecondName();
    }
}
