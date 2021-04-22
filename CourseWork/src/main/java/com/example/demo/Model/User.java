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
     * пустой конструктор
     */
    public User() {
    }

    /**
     * параметризованный конструктор
     * @param username никнейм пользователя
     * @param email адрес эл.почты
     * @param password пароль
     * @param phoneNumber номер телефона
     */
    public User(String username, String email, String password, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    /**
     * получение никнейма пользователя
     * @return username - никнейм пользователя
     */
    public String getUsername() {
        return username;
    }

    /**
     * установка никнейма пользователя
     * @param username никнейм пользователя
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * получение адреса эл.почты
     * @return email - адрес эл.почты
     */
    public String getEmail() {
        return email;
    }

    /**
     * становка адреса эл.почты
     * @param email адрес эл.почты
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * получение пароля
     * @return password - пароль
     */
    public String getPassword() {
        return password;
    }

    /**
     * установка пароля
     * @param password пароль
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * получение номера телефона
     * @return phoneNumber - номер телефона
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * становка номера телефона
     * @param phoneNumber номер телефона
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
