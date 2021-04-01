package com.example.demo.Controllers;

import com.example.demo.Model.Order;
import com.example.demo.Model.User;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.OrderService;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Контроллер, отвечающий за весь
 * функционал личного кабинета
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    /**
     * Возврат домашней страницы
     *
     * @return a {@link java.lang.String} object.
     */
    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    /**
     *Возврат страницы личного кабинета
     *
     * @param httpServletResponse a {@link javax.servlet.http.HttpServletResponse} object.
     * @param model модель страницы main
     * @return страницу личного кабинета
     */
    @GetMapping("/main")
    public String mainPage(HttpServletResponse httpServletResponse, Model model) {
        //userService.createCookie(httpServletResponse);
        List<Order> userOrders = orderService.findAllByCustomerUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("orders", userOrders);
        return "main";
    }

    /**
     *Возврат страницы для смены пароля
     *
     * @return страницу для смены личного кабинета
     */
    @GetMapping("/changePassword")
    public String changePassword() {
        return "changePage";
    }

    /**
     * Процесс смены пароля с необходимыми проверками
     *
     * @param oldPassword a {@link java.lang.String} object.
     * @param newPassword a {@link java.lang.String} object.
     * @param model a {@link org.springframework.ui.Model} object.
     * @return a {@link java.lang.String} object.
     */
    @PostMapping("/changePasswordAction")
    public String changePasswordAction(@ModelAttribute("old_password") String oldPassword,
                                       @ModelAttribute("password") String newPassword,
                                       Model model) {
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userService.matches(oldPassword, user.getPassword())) {
            if (newPassword.length() > 5) {
                if (oldPassword.equals(newPassword)) {
                    model.addAttribute("errorMessage", "старый и новый пароли должны отличаться");
                    return "/changePage";
                }
                else {
                    user.setPassword(newPassword);
                    userService.save(user);
                    return "redirect:/main";
                }
            }
            else {
                model.addAttribute("errorMessage", "пароль не должен быть короче 6 символов");
                return "/changePage";
            }
        }
        else {
            model.addAttribute("errorMessage", "неверный старый пароль");
            return "/changePage";
        }
    }
}
