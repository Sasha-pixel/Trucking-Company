package com.example.demo.Controllers;

import com.example.demo.Model.User;
import com.example.demo.Roles.Role;
import com.example.demo.Services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер, отвечающий за регистрацию и атворизацию
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Controller
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;

    /**
     * Возврат страницы авторизации
     *
     * @param user объект авторизированного пользователя
     * @return страницу авторизации
     */
    @GetMapping("/login")
    public String authorizationPage(@AuthenticationPrincipal User user, Model model) {
        return authorizationService.checkAuthority(user, model);
    }

    /**
     *Возврат страницы регистрации
     *
     * @param user объект авторизированного пользовател
     * @param model a {@link org.springframework.ui.Model} object.
     * @return страницу регистрации
     */
    @GetMapping("/registration")
    public String registration(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("userForm", new User());
        return "registration";
    }

    /**
     * Непосредственная регистрация нового пользователя с проверкой вводимых данных
     *
     * @param userForm a {@link com.example.demo.Model.User} object - объект пользователя из формы.
     * @param bindingResult a {@link org.springframework.validation.BindingResult} object - лист ошибок.
     * @param model a {@link org.springframework.ui.Model} object.
     * @return переадресация на домашнюю страницу
     */
    @PostMapping("/registrationAction")
    public String registrationAction(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        return authorizationService.save(userForm, bindingResult, model);
    }

    /**
     * метод активации учётной записи
     * @param code код активации учётной записи
     * @param model модель веб-страницы
     * @return страница авторизации
     */
    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean isActivated = authorizationService.activateUser(code);
        if (isActivated)
            model.addAttribute("activate_success", "Учётная запись успешно активирована");
        else
            model.addAttribute("activate_fail", "Активация учётной записи не удалась");
        return "login";
    }
}
