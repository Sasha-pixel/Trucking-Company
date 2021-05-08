package com.example.demo.Services;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Order;
import com.example.demo.Model.Truck;
import com.example.demo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис, реализующий логику работы страницы администратора
 *
 * @author kanenkovaa
 */
@Service
public class AdminService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TruckService truckService;

    /**
     * Метод получения страницы администратора
     * @param user объект пользователя(администратора)
     * @param model модель веб-страницы
     * @return
     */
    public String getAdminPage(User user, Model model) {
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
     * Метод проверки активации учётной записи пользователя
     * @param user объект пользователя(администратора)
     * @param model модель веб-страницы
     */
    public void checkUserActivationCode(User user, Model model) {
        if (user.getActivationCode() != null)
            model.addAttribute("notActivated", "Вы не активировали учётную запись," +
                    " в связи с этим, некоторые функции личного кабинета недоступны");
        model.addAttribute("user", user);
    }

    /**
     * Метод осуществляющий поиск пользователей и их заказов по введённой подстроке имени пользователя
     * @param username подстрока имени пользователя
     * @param model модель веб страницы
     * @return перенаправление на страницу администатора или возврат страницы администратора
     */
    public String searchUsers(String username, Model model) {
        if (username.isEmpty()) {
            return "redirect:/admin/main";
        }
        else {
            List<Order> orderList = new ArrayList<>();
            for (Order order1 : orderService.findAll()) {
                if (order1.getCustomerUsername().toUpperCase().contains(username.toUpperCase()))
                    orderList.add(order1);
            }
            model.addAttribute("username", username);
            model.addAttribute("isChecking", "users");
            model.addAttribute("allUsersOrders", orderList);
            model.addAttribute("allEmployees", employeeService.findAll());
            return "admin";
        }
    }

    /**
     * Метод осуществляющий поиск сотрудников по подстроке их имени и фамилии
     * @param employee подстрока имени и фамилии
     * @param model модель веб-страницы
     * @return перенаправление на страницу администатора или возврат страницы администратора
     */
    public String checkEmployees(String employee, Model model) {
        if (employee.isEmpty()) {
            return "redirect:/admin/main";
        }
        else {
            List<Employee> employeeList = new ArrayList<>();
            for (Employee employee1 : employeeService.findAll()) {
                if (employee1.getName().toUpperCase().contains(employee.toUpperCase()))
                    employeeList.add(employee1);
            }
            model.addAttribute("employee", employee);
            model.addAttribute("isChecking", "employees");
            model.addAttribute("allEmployees", employeeList);
            model.addAttribute("allUsersOrders", orderService.findAllByOrderByCustomerUsername());
            return "admin";
        }
    }

    /**
     * Метод добавления нового автомобиля
     * @param truck объект автомобиля
     * @param bindingResult лист ошибок валидации
     * @param model модель веб-страницы
     * @return перенаправление на страницу администатора или возврат страницы добавления новых автомобилей и сотрудников
     */
    public String addingCar(Truck truck, User user, BindingResult bindingResult, Model model) {
        if (!truckService.validateTruck(truck, bindingResult, model)) {
            truckService.save(truck);
            return "redirect:/admin/main";
        }
        else {
            model.addAttribute("carNumber_paste", truck.getCarNumber());
            model.addAttribute("user", user);
            return "newEmployeeOrCar";
        }
    }

    /**
     * Метод добавления нового сотрудника
     * @param employee объект сотрудника
     * @param bindingResult лист ошибок валидации
     * @param model модель веб-страницы
     * @return перенаправление на страницу администатора или возврат страницы добавления новых автомобилей и сотрудников
     */
    public String addingEmployee(Employee employee, User user, BindingResult bindingResult, Model model) {
        if (!employeeService.validateEmployee(employee, bindingResult, model)) {
            employeeService.save(employee);
            return "redirect:/admin/main";
        }
        else {
            model.addAttribute("name_paste", employee.getName());
            model.addAttribute("user", user);
            return "newEmployeeOrCar";
        }
    }
}
