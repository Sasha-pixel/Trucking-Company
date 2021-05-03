package com.example.demo.Controllers;

import com.example.demo.Model.Order;
import com.example.demo.Model.User;
import com.example.demo.Roles.Role;
import com.example.demo.Services.OrderService;
import com.example.demo.Services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private AuthorizationService authorizationService;

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
    public String mainPage(HttpServletResponse httpServletResponse,
                           @AuthenticationPrincipal User user,
                           Model model) {
        //userService.createCookie(httpServletResponse);
//        if (user.getRoles().contains(Role.ADMIN)) {
//            if (user.getActivationCode() != null)
//                model.addAttribute("notActivated", "Вы не активировали учётную запись," +
//                        " в связи с этим, некоторые функции личного кабинета недоступны");
//            return "redirect:/admin/main";
//        }
//        else {
//            if (user.getActivationCode() != null)
//                model.addAttribute("notActivated", "Вы не активировали учётную запись," +
//                        " в связи с этим, некоторые функции личного кабинета недоступны");
//            List<Order> userOrders = orderService.findAllByCustomerUsername(user.getUsername());
//            model.addAttribute("orders", userOrders);
//            model.addAttribute("user", user);
//            return "mainUser";
        return authorizationService.getMainPage(user, model);
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
//        User user = authorizationService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        if (authorizationService.matches(oldPassword, user.getPassword())) {
//            if (newPassword.length() > 5) {
//                if (oldPassword.equals(newPassword)) {
//                    model.addAttribute("errorMessage", "старый и новый пароли должны отличаться");
//                    return "/changePage";
//                }
//                else {
//                    user.setPassword(newPassword);
//                    authorizationService.updatePassword(user);
//                    return "redirect:/main";
//                }
//            }
//            else {
//                model.addAttribute("errorMessage", "пароль не должен быть короче 6 символов");
//                return "/changePage";
//            }
//        }
//        else {
//            model.addAttribute("errorMessage", "неверный старый пароль");
//            return "/changePage";
//        }
        return authorizationService.changingPassword(oldPassword, newPassword, model);
    }
}
