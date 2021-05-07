package com.example.demo.Controllers;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Order;
import com.example.demo.Model.Truck;
import com.example.demo.Model.User;
import com.example.demo.Services.EmployeeService;
import com.example.demo.Services.OrderService;
import com.example.demo.Services.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TruckService truckService;

    /**
     * Возврат страницы администратора
     *
     * @param model модель веб-страницы
     * @return admin - страница администратора
     */
    @GetMapping("/main")
    public String adminPage(@AuthenticationPrincipal User user, Model model) {
        if (user.getActivationCode() != null)
            model.addAttribute("notActivated", "Вы не активировали учётную запись," +
                    " в связи с этим, некоторые функции личного кабинета недоступны");
        model.addAttribute("user", user);
        model.addAttribute("isChecking", "users");
        model.addAttribute("allUsersOrders", orderService.findAllByOrderByCustomerUsername());
        model.addAttribute("allEmployees", employeeService.findAll());
        return "admin";
    }

    /**
     * метод поиска заказов указанного пользователя
     * @param user авторизированный пользователь
     * @param username имя пользователя, заказы которого нужно найти
     * @param model модель веб-страницы
     * @return перенаправление на страницу администратора или возврат страницы администратора
     */
    @GetMapping("/searchByUsername")
    public String searchForUsername(@AuthenticationPrincipal User user, @RequestParam("username") String username, Model model) {
        if (user.getActivationCode() != null)
            model.addAttribute("notActivated", "Вы не активировали учётную запись," +
                    " в связи с этим, некоторые функции личного кабинета недоступны");
        model.addAttribute("user", user);
        if (username.isEmpty()) {
            return "redirect:/admin/main";
        }
        else {
            model.addAttribute("username", username);
            model.addAttribute("isChecking", "users");
            model.addAttribute("allUsersOrders", orderService.searchOrdersByUsername(username));
            model.addAttribute("allEmployees", employeeService.findAll());
            return "admin";
        }
    }

    /**
     * метод поиска сотрудников
     * @param user авторизированный пользователь
     * @param employee имя сотрудника
     * @param model модель веб-страницы
     * @return перенаправление на страницу администратора или возврат страницы администратора
     */
    @GetMapping("/searchEmployee")
    public String searchEmployee(@AuthenticationPrincipal User user, @RequestParam("employee") String employee, Model model) {
        if (user.getActivationCode() != null)
            model.addAttribute("notActivated", "Вы не активировали учётную запись," +
                    " в связи с этим, некоторые функции личного кабинета недоступны");
        model.addAttribute("user", user);
        if (employee.isEmpty()) {
            return "redirect:/admin/main";
        }
        else {
            model.addAttribute("employee", employee);
            model.addAttribute("isChecking", "employees");
            model.addAttribute("allEmployees", employeeService.searchEmployee(employee));
            model.addAttribute("allUsersOrders", orderService.findAllByOrderByCustomerUsername());
            return "admin";
        }
    }

    @GetMapping("addNewEmployeeOrCar")
    public String getPageAddNewEmployeeOrCar(Model model) {
        model.addAttribute("car-form", new Truck());
        model.addAttribute("employee-form", new Employee());
        return "newEmployeeOrCar";
    }

    @PostMapping("/addCar")
    public String addNewCar(@ModelAttribute("car-form") Truck truck,
                            BindingResult bindingResult,
                            Model model) {
        if (!truckService.validateTruck(truck, bindingResult, model)) {
            truckService.save(truck);
            return "redirect:/admin/main";
        }
        else {
            model.addAttribute("carNumber_paste", truck.getCarNumber());
            return "newEmployeeOrCar";
        }
    }

    @PostMapping("/addEmployee")
    public String addNewEmployee(@ModelAttribute("employee-form") Employee employee,
                            BindingResult bindingResult,
                            Model model) {
        if (!employeeService.validateEmployee(employee, bindingResult, model)) {
            employeeService.save(employee);
            return "redirect:/admin/main";
        }
        else {
            model.addAttribute("name_paste", employee.getName());
            return "newEmployeeOrCar";
        }
    }
}
