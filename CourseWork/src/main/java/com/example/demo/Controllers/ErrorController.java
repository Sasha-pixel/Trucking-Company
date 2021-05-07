package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Класс контроллера, обрабатывающий ошибку
 */
@Controller
public class ErrorController {

    /**
     * Метод, возвращающий страницу error
     * @return веб-страница error
     */
    @GetMapping("/error")
    public String getErrorPage() {
        return "error";
    }
}
