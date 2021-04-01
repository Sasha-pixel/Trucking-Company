package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Точка входа.
 *
 * @author kanenkovaa
 * @version 0.1
 */
@SpringBootApplication
public class DemoApplication {

    /**
     * Запуск приложения
     *
     * @param args an array of {@link java.lang.String} objects.
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
