package com.example.demo.Controllers;

import com.example.demo.Model.User;
import com.example.demo.Roles.Role;
import com.example.demo.Services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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
     * @return страницу авторизации
     */
    @GetMapping("/login")
    public String authorizationPage() {
//        if (authorizationService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()) != null)
//            return "redirect:/main";
//        return "login";
        return authorizationService.checkAuthority();
    }

    //@GetMapping("/createCookie")


//    @PostMapping("/registrationAction")
//    public String registrationPage(@PathParam("username") String username,
//                                   @PathParam("email") String email,
//                                   @PathParam("phoneNumber") String phoneNumber,
//                                   @PathParam("password") String password) {
//        User user = new User(username, email, password, phoneNumber);
//        List<User> userList = userRepository.findAllByUsername(username);
//        userList.addAll(userRepository.findAllByEmail(email));
//        userList.addAll(userRepository.findByPhoneNumber(phoneNumber));
//        if (userList.isEmpty())
//            userRepository.save(user);
//        return "redirect:/login";
//    }

    /**
     *Возврат страницы регистрации
     *
     * @param model a {@link org.springframework.ui.Model} object.
     * @return страницу регистрации
     */
    @GetMapping("/registration")
    public String registration(Model model) {
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
//        if (authorizationService.validateUserForm(userForm, bindingResult, model)) {
//            authorizationService.pasteUserForm(userForm, model);
//            return "registration";
//        }
//        userForm.setRoles(Collections.singleton(Role.USER));
//        authorizationService.save(userForm);
//        return "redirect:/main";
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
