package com.example.demo.Repositories;

import com.example.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * JPA репозиторий для пользователей
 *
 * @author kanenkovaa
 * @version 0.1
 */
public interface UserRepository extends JpaRepository<User, Long> {

   /**
    * Поиск пользователя в бд по его никнейму
    *
    * @param username a {@link java.lang.String} object.
    * @return a {@link com.example.demo.Model.User} object.
    */
   User findByUsername(String username);

   /**
    * Поиск пользователя по его адресу эл.почты
    *
    * @param email a {@link java.lang.String} object.
    * @return a {@link com.example.demo.Model.User} object.
    */
   User findByEmail(String email);

   /**
    * Поиск пользователя по его номеру телефона
    *
    * @param phoneNumber a {@link java.lang.String} object.
    * @return a {@link com.example.demo.Model.User} object.
    */
   User findByPhoneNumber(String phoneNumber);

   /**
    * Поиск пользователя по коду активации учётной записи
    * @param code код активации учётной записи
    * @return a {@link com.example.demo.Model.User} object.
    */
    User findByActivationCode(String code);
}
