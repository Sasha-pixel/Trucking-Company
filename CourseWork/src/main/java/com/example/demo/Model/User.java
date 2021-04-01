package com.example.demo.Model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.constraints.Email;

/**
 * Модель пользователя
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Entity
@Table(schema = "public", name = "user8")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String role;

    /**
     * <p>Constructor for User.</p>
     */
    public User() {
    }

    /**
     * <p>Constructor for User.</p>
     *
     * @param username a {@link java.lang.String} object.
     * @param email a {@link java.lang.String} object.
     * @param password a {@link java.lang.String} object.
     * @param phoneNumber a {@link java.lang.String} object.
     */
    public User(String username, String email, String password, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    /**
     * <p>Getter for the field <code>username</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getUsername() {
        return username;
    }

    /**
     * <p>Setter for the field <code>username</code>.</p>
     *
     * @param username a {@link java.lang.String} object.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * <p>Getter for the field <code>email</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getEmail() {
        return email;
    }

    /**
     * <p>Setter for the field <code>email</code>.</p>
     *
     * @param email a {@link java.lang.String} object.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * <p>Getter for the field <code>password</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPassword() {
        return password;
    }

    /**
     * <p>Setter for the field <code>password</code>.</p>
     *
     * @param password a {@link java.lang.String} object.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>Getter for the field <code>phoneNumber</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * <p>Setter for the field <code>phoneNumber</code>.</p>
     *
     * @param phoneNumber a {@link java.lang.String} object.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
