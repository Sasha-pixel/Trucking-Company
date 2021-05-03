package com.example.demo.Services;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Order;
import com.example.demo.Model.Truck;
import com.example.demo.Repositories.OrderRepository;
import com.example.demo.Validators.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Сервис, осуществляющий связь контроллера заказов с таблицей заказов
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderValidator orderValidator;

    /**
     * метод сохранения нового заказа в бд
     *
     * @param order объект заказа
     */
    public void save(Order order) {
        orderRepository.save(order);
    }

    /**
     * получение данных о всех заказах пользователя
     *
     * @param customerUsername никней пользователя
     * @return лист заказов
     */
    public List<Order> findAllByCustomerUsername(String customerUsername) {
        return orderRepository.findAllByCustomerUsername(customerUsername);
    }

    /**
     * удаление заказа по его id
     * @param id id заказа
     */
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    /**
     * Метод для получения списка всех заказов, отсортированных по никнейму заказчика
     * @return отсортированный список всех заказов
     */
    public List<Order> findAllByOrderByCustomerUsername() {
        return orderRepository.findAllByOrderByCustomerUsername();
    }

    /**
     * Вставка в поля формы оформления заказа
     * @param orderForm объект заказа
     * @param numberOfWorkers количество требующихся работников
     * @param model модель веб-страницы
     */
    public void pasteOrderForm(Order orderForm, int numberOfWorkers, Model model) {
        model.addAttribute("city_paste", orderForm.getCity());
        model.addAttribute("street_paste", orderForm.getStreet());
        model.addAttribute("building_paste", orderForm.getBuilding());
        model.addAttribute("apartment_paste", orderForm.getApartment());
        model.addAttribute("targetDate_paste", orderForm.getTargetDate());
        model.addAttribute("numberOfWorkers_paste", numberOfWorkers);
    }

    public boolean validateOrderForm(Order orderForm, List<Employee> workersBuf, int numberOfWorkers, Truck truck, BindingResult bindingResult, Model model) {
        orderValidator.customValidate(orderForm, numberOfWorkers, workersBuf, truck, bindingResult);
        if (bindingResult.hasErrors()) {
            for (Object object : bindingResult.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError)object;
                    model.addAttribute(fieldError.getField(), fieldError.getCode());
                }
            }

            return true;
        }
        else
            return false;
    }
}
