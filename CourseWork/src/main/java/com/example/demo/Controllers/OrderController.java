package com.example.demo.Controllers;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Order;
import com.example.demo.Model.Truck;
import com.example.demo.Services.EmployeeService;
import com.example.demo.Services.OrderService;
import com.example.demo.Services.TruckService;
import com.example.demo.Validators.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    private TruckService truckService;

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
    public String makeOrderAction(@ModelAttribute Order orderForm,
                                  @RequestParam("truckDescription") String truckDescription,
                                  @RequestParam("numberOfWorkers") int numberOfWorkers,
                                  BindingResult bindingResult, Model model) {
        List<Employee> workers = employeeService.findAll();
        List<Employee> workersBuf = employeeService.setWorkersToOrder(orderForm, workers);
        List<Truck> trucks = truckService.findAllByDescription(truckDescription);
        Truck truck = truckService.setTruckToOrder(orderForm, trucks);
//        orderValidator.validate(orderForm, bindingResult);
//        if (bindingResult.hasErrors()) {
//            for (Object object : bindingResult.getAllErrors()) {
//                if (object instanceof FieldError) {
//                    FieldError fieldError = (FieldError)object;
//                    model.addAttribute(fieldError.getField(), fieldError.getCode());
//                }
//            }
//            orderService.pasteOrderForm(orderForm, numberOfWorkers, model);
//            return "makeOrder";
//        }
        if (orderService.validateOrderForm(orderForm, workersBuf, numberOfWorkers, truck, bindingResult, model)) {
            orderService.pasteOrderForm(orderForm, numberOfWorkers, model);
            return "makeOrder";
        }
        if (orderForm.getApartment().isEmpty())
            orderForm.setApartment("-");
        orderForm.setCustomerUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        orderForm.setCreationDate(new Date());
//        List<Employee> workers = employeeService.findAll();
//        List<Employee> workersBuf = new ArrayList<>();
//        boolean flag;
//        for (Employee worker : workers) {
//            flag = true;
//            if (worker.getOrders().isEmpty())
//                workersBuf.add(worker);
//            else {
//                for (Order order : worker.getOrders()) {
//                    if (order.getTargetDate().equals(orderForm.getTargetDate()))
//                        flag = false;
//                }
//                if (flag)
//                    workersBuf.add(worker);
//            }
//        }
//        List<Employee> workersBuf = employeeService.setWorkersToOrder(orderForm, workers);
//        if(numberOfWorkers > workersBuf.size()) {
//            model.addAttribute("workers", "Не хватает свободных грузчиков, попробуйте изменить дату или уменьшить количество требующихся грузчиков");
//            return "makeOrder";
//        }
//        List<Truck> trucks = truckService.findAllByDescription(truckDescription);
//        if (trucks.isEmpty()) {
//            model.addAttribute("truck", "Не хватает свободных автомобилей, попробуйте изменить дату или сменить тип автомобиля");
//            return "makeOrder";
//        }
//        for (Truck truck : trucks) {
//            flag = true;
//            if (truck.getOrders().isEmpty()) {
//                orderForm.setTruck(truck);
//                break;
//            }
//            else {
//                for (Order order : truck.getOrders()) {
//                    if (order.getTargetDate().equals(orderForm.getTargetDate()))
//                        flag = false;
//                }
//                if (flag) {
//                    orderForm.setTruck(truck);
//                    break;
//                }
//            }
//        }
        orderForm.setTruck(truck);
        orderForm.setWorkers(workersBuf.subList(0, numberOfWorkers));
        orderService.save(orderForm);
        return "redirect:/main";
    }

    /**
     * метод удаления заказа
     * @param id номер заказа
     * @return перенаправление на странцу личного кабинета
     */
    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        orderService.delete(id);
        return "redirect:/main";
    }
}
