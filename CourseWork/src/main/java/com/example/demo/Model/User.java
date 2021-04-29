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

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

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
     * @param password пароль
     * @param phoneNumber номер телефона
     * @param roles набор ролей
     */
    public User(String username, String email, String password, String phoneNumber, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
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
