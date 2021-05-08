package com.example.demo.Services;

import com.example.demo.Model.Order;
import com.example.demo.Model.User;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Roles.Role;
import com.example.demo.Validators.AuthorizationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Сервис, осуществляющий связь контроллеров и таблицы с пользователями
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Service
public class AuthorizationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthorizationValidator authorizationValidator;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private OrderService orderService;

    /**
     * Кодирование пароля
     *
     * @param password a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    /**
     * метод проверки кодированного пароля
     * @param rawPassword новый пароль
     * @param oldPassword старый пароль
     * @return boolean
     */
    public boolean matches(String rawPassword, String oldPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, oldPassword);
    }

    /**
     * Метод для получения страницы авторизации
     *
     * @param user объект авторизированного пользователя
     * @param model модель веб-страницы
     * @return страницу авторизации или перенаправление на страницу личного кабинета
     */
    public String checkAuthority(String error, User user, Model model, HttpServletRequest request) {
        model.addAttribute("user", user);
        if (user != null)
            return "redirect:/main";
        if(error != null){
            model.addAttribute("IncorrectData", "Неправильный логин или пароль");
        }
        else{
            Cookie[] cookies = request.getCookies();
            if(cookies!=null)
                for (Cookie c: cookies){
                    if(c.getName().equals("password"))
                        model.addAttribute("password", c.getValue());
                    if(c.getName().equals("login"))
                        model.addAttribute("username", c.getValue());
                }
        }
        return "login";
    }

    /**
     * Получение страницы личного кабинета
     * @param user объект пользователя
     * @param model модель веб-страницы
     * @return перенаправление на страницу администратора или получение страницы личного кабинета пользователя
     */
    public String getMainPage(User user, Model model) {
        if (user.getRoles().contains(Role.ADMIN)) {
            return "redirect:/admin/main";
        }
        else {
            if (user.getActivationCode() != null)
                model.addAttribute("notActivated", "Вы не активировали учётную запись," +
                        " в связи с этим, некоторые функции личного кабинета недоступны");
            List<Order> userOrders = orderService.findAllByCustomerUsername(user.getUsername());
            model.addAttribute("orders", userOrders);
            model.addAttribute("user", user);
            return "mainUser";
        }
    }

    /**
     * Сохранение нового пользователя
     *
     * @param user a {@link com.example.demo.Model.User} object.
     */
    public String save(User user, BindingResult bindingResult, Model model) {
        if (validateUserForm(user, bindingResult, model)) {
            pasteUserForm(user, model);
            return "registration";
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        String message = String.format(
                "Здравствуйте, %s \n" +
                        "Пожалуйста, перейдите по следующей ссылке, чтобы активировать свою учётную запись: " +
                        "http://localhost:8087/activate/%s",
                user.getUsername(),
                user.getActivationCode()
        );
        mailSender.send(user.getEmail(), "Активация учётной записи", message);
        return "redirect:/main";
    }

    /**
     * обновление пароля
     * @param user объект пользователя
     * @return перенаправление на страницу личного кабинета
     */
    public String updatePassword(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/main";
    }

    /**
     * Поиск по никнейму пользователя
     *
     * @param username a {@link java.lang.String} object.
     * @return a {@link com.example.demo.Model.User} object.
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Поиск пользователя по адресу эл.почты
     *
     * @param email a {@link java.lang.String} object.
     * @return a {@link com.example.demo.Model.User} object.
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Посик пользователя по номеру телефона
     *
     * @param phoneNumber a {@link java.lang.String} object.
     * @return a {@link com.example.demo.Model.User} object.
     */
    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    /**
     * Создание и сохранение cookie файлов
     *
     * @param httpServletResponse a {@link javax.servlet.http.HttpServletResponse} object.
     */
    public void createCookie(HttpServletResponse httpServletResponse) {
        User user = findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Cookie usernameCookie = new Cookie("username", user.getUsername());
        Cookie passwordCookie = new Cookie("password", user.getPassword());
        httpServletResponse.addCookie(usernameCookie);
        httpServletResponse.addCookie(passwordCookie);
    }

    /**
     * Вставка в поля формы регистрации
     * @param userForm объект пользователя
     * @param model модель веб-страницы
     */
    public void pasteUserForm(User userForm, Model model) {
        model.addAttribute("username_paste", userForm.getUsername());
        model.addAttribute("email_paste", userForm.getEmail());
        model.addAttribute("phoneNumber_paste", userForm.getPhoneNumber());
    }

    /**
     * Метод валидации объекта пользователя, образованного по форме регитсрации
     * @param userForm объект пользователя из формы
     * @param bindingResult a {@link org.springframework.validation.BindingResult} object - лист ошибок.
     * @param model a {@link org.springframework.ui.Model} object.
     * @return boolean
     */
    public boolean validateUserForm(User userForm, BindingResult bindingResult, Model model) {
        authorizationValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            for (Object object : bindingResult.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    model.addAttribute(fieldError.getField(), fieldError.getCode());
                }
            }
            return true;
        }
        else
            return false;
    }


    /**
     * активация учётной записи пользователя
     * @param code код активации учётной записи
     * @return boolean
     */
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null)
            return false;
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }

    /**
     * метод смены пароля
     * @param oldPassword старый пароль
     * @param newPassword новый пароь
     * @param model модель веб-страницы
     * @return в случае ошибки возвращается страница смены пароля, при успешной смене пароля происходит перенаправление на страницу личного кабинета
     */
    public String changingPassword(String oldPassword, String newPassword, Model model) {
        User user = findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (matches(oldPassword, user.getPassword())) {
            if (newPassword.length() > 5) {
                if (oldPassword.equals(newPassword)) {
                    model.addAttribute("errorMessage", "старый и новый пароли должны отличаться");
                    model.addAttribute("user", user);
                    return "/changePage";
                }
                else {
                    user.setPassword(newPassword);
                    updatePassword(user);
                    return "redirect:/main";
                }
            }
            else {
                model.addAttribute("errorMessage", "пароль не должен быть короче 6 символов");
                model.addAttribute("user", user);
                return "/changePage";
            }
        }
        else {
            model.addAttribute("errorMessage", "неверный старый пароль");
            model.addAttribute("user", user);
            return "/changePage";
        }
    }
}
