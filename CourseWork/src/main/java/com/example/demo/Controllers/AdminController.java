package com.example.demo.Controllers;

import com.example.demo.Model.Order;
import com.example.demo.Model.User;
import com.example.demo.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String adminPage(@AuthenticationPrincipal User user, Model model) {
        List<Order> allOrders = orderService.findAllByOrderByCustomerUsername();
        if (user.getActivationCode() != null)
            model.addAttribute("notActivated", "Вы не активировали учётную запись," +
                    " в связи с этим, некоторые функции личного кабинета недоступны");
        model.addAttribute("user", user);
        model.addAttribute("allOrders", allOrders);
        return "admin";
    }
}
