package com.example.demo.Controllers;

import com.example.demo.Model.User;
import com.example.demo.Services.OrderService;
import com.example.demo.Services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

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
    public String homePage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
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
        return authorizationService.getMainPage(user, model);
    }

    /**
     *Возврат страницы для смены пароля
     *
     * @return страницу для смены личного кабинета
     */
    @GetMapping("/changePassword")
    public String changePassword(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
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
        return authorizationService.changingPassword(oldPassword, newPassword, model);
    }
}
