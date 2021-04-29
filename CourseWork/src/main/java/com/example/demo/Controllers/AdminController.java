package com.example.demo.Controllers;

import com.example.demo.Model.Order;
import com.example.demo.Model.User;
import com.example.demo.Repositories.OrderRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Roles.Role;
import com.example.demo.Services.OrderService;
import com.example.demo.Services.UserService;
import com.example.demo.Validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.Collections;
import java.util.List;

/**
 * Контроллер админа
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private OrderService orderService;

    /**
     * Возврат страницы администратора
     *
     * @param model модель веб-страницы
     * @return admin - страница администратора
     */
    @GetMapping("/main")
    public String adminPage(Model model) {
        List<Order> allOrders = orderService.findAllByOrderByCustomerUsername();
        model.addAttribute("allOrders", allOrders);
        return "admin";
    }
}
