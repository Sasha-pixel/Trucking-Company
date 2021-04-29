package com.example.demo.Configuration;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Конфигурация MVC
 *
 * @author kanenkovaa
 * @version 0.1
 */
public class MvcConfig implements WebMvcConfigurer {

    /**
     *
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/registration").setViewName("registration");
    }
}
