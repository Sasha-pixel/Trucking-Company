package com.example.demo.Controllers;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
/**
 * Контроллер для shutdown endpoint для корректного закрытия сессий
 *
 * @author kanenkovaa
 * @version 0.1
 */
public class EndController implements ApplicationContextAware {

    private ApplicationContext context;

    /** Получение контекста */
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;
    }

    /**
     * Закрытие
     */
    @GetMapping(value = "/end")
    public void shutdownContext() {
        ((ConfigurableApplicationContext) context).close();
    }

}
