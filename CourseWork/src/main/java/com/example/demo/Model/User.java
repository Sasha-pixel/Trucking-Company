package com.example.demo.Model;

import com.example.demo.Roles.Role;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.Set;

/**
 * Модель пользователя
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Entity
@Table(schema = "public", name = "user8")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role8", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    /**
     * пустой конструктор
     */
    public User() {
    }

    /**
     * параметризованный конструктор
     * @param username никнейм пользователя
     * @param email адрес эл.почты
     * @param activationCode код для активации учётной записи
     * @param password пароль
     * @param phoneNumber номер телефона
     * @param resetPasswordToken токен для восстановления пароля
     * @param roles набор ролей
     */
    public User(String username, String email, String activationCode, String password, String phoneNumber, String resetPasswordToken, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.activationCode = activationCode;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.resetPasswordToken = resetPasswordToken;
        this.roles = roles;
    }



    /**
     * получение никнейма пользователя
     * @return username - никнейм пользователя
     */
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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
     * получение кода активаии учётной записи
     * @return activationCode - код активации учётной записи
     */
    public String getActivationCode() {
        return activationCode;
    }

    /**
     * установка кода активации учётной записи
     * @param activationCode код активации учётной записи
     */
    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    /**
     * получение ролей пользователя
     * @return roles - набор ролей
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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
     * установка номера телефона
     * @param phoneNumber номер телефона
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * получение токена для смены пароля
     * @return токен для смены пароля
     */
    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    /**
     * установка токена для смены пароля
     * @param resetPasswordToken токен для смены пароля
     */
    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    /**
     * получение набора ролей
     * @return roles - набор ролей
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * установка набора ролей
     * @param roles набор ролей
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
