package com.example.demo.Controllers;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Order;
import com.example.demo.Services.EmployeeService;
import com.example.demo.Services.OrderService;
import com.example.demo.Services.UserService;
import com.example.demo.Validators.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

/**
 * Контроллер, отвечающий за создание нового заказа
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OrderValidator orderValidator;

    /**
     * Метод для получения страницы оформления заказа
     * @param model модель страницы makeOrder
     * @return страница с формой заказа
     */
    @GetMapping("/makeOrder")
    public String makeOrder(Model model) {
        model.addAttribute("orderForm", new Order());
        return "makeOrder";
    }

    /**
     * Метод для оформления нового заказа
     *
     * @param orderForm объект нового заказа
     * @param bindingResult лист ошибок, возникших при проверке формы
     * @param model модель страницы makeOrder
     * @return перенаправление на /main
     */
    @PostMapping("/makeOrderAction")
    public String makeOrderAction(/*@ModelAttribute("city") String city,
                                  @ModelAttribute("street") String street,
                                  @ModelAttribute("building") String building,
                                  @ModelAttribute("apartment") String apartment,
                                  @ModelAttribute("targetDate") String targetDate,*/
                                  @ModelAttribute("orderForm") Order orderForm,
                                  //@ModelAttribute("numberOfWorkers") int numberOfWorkers,
                                  BindingResult bindingResult, Model model) {
        //Order orderForm = new Order(city, street, building, apartment, targetDate);
        orderValidator.validate(orderForm, bindingResult);
        if (bindingResult.hasErrors()) {
            for (Object object : bindingResult.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError)object;
                    model.addAttribute(fieldError.getField(), fieldError.getCode());
                }
            }
            return "makeOrder";
        }
        orderForm.setCustomerUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        orderForm.setCreationDate(new Date());
        List<Employee> workers = employeeService.findAll();
        orderForm.setWorkers(workers.subList(0, 2));
        orderService.save(orderForm);
        return "redirect:/main";
    }
}
