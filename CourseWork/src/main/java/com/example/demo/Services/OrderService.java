package com.example.demo.Services;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Order;
import com.example.demo.Model.Truck;
import com.example.demo.Model.User;
import com.example.demo.Repositories.OrderRepository;
import com.example.demo.Validators.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private MailSender mailSender;

    /**
     * метод сохранения нового заказа в бд
     *
     * @param order объект заказа
     */
    public void save(Order order) {
        User user = authorizationService.findByUsername(order.getCustomerUsername());
        String message = "Здравствуйте, " + order.getCustomerUsername() + ". Вами был оформлен заказ." + '\n' +
                "Информация о заказе: " + '\n' +
                "Пункт отправки: " + order.getAddressFrom() + '\n' +
                "Пункт назначения: " + order.getAddressTo() + '\n' +
                "Дата оформления заказа: " + order.getCreationDate() + '\n' +
                "Дата выполнения заказа: " + order.getTargetDate() + '\n' +
                "Примерная стоимость: " + order.getPrice() + " руб." + '\n' + '\n' +
                "Спасибо, что выбрали нас!";
        mailSender.send(user.getEmail(), "Новый заказ", message);
        orderRepository.save(order);
    }

    /**
     * Метод для получение списка всех заказов
     * @return список всех заказов
     */
    public List<Order> findAll() {
        return orderRepository.findAll();
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
        model.addAttribute("addressFrom_paste", orderForm.getAddressFrom());
        model.addAttribute("addressTo_paste", orderForm.getAddressTo());
        model.addAttribute("distance_paste", orderForm.getDistance());
        model.addAttribute("duration_paste", orderForm.getDuration());
        model.addAttribute("targetDate_paste", orderForm.getTargetDate());
        if (orderForm.getTargetTime().length() == 5) {
            model.addAttribute("hours_paste", orderForm.getTargetTime().split(":")[0]);
            model.addAttribute("minutes.paste", orderForm.getTargetTime().split(":")[1]);
        }
        model.addAttribute("targetTime_paste", orderForm.getTargetTime());
        model.addAttribute("numberOfWorkers_paste", numberOfWorkers);
        model.addAttribute("price_paste", orderForm.getPrice());
    }

    /**
     * Проверка полей формы оформления заказа
     * @param orderForm объект заказа
     * @param workersBuf список сотрудников
     * @param numberOfWorkers количество сотрудников, требующееся для выполнения заказа
     * @param truck объект автомобиля
     * @param bindingResult лист ошибок
     * @param model модель веб-страницы
     * @return boolean
     */
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

    /**
     * Поиск заказов по подстроке никнейма заказчика
     * @param username подстрока никнейма заказчика
     * @return список заказов
     */
    public List<Order> searchOrdersByUsername(String username) {
        return orderRepository.findAllByCustomerUsernameContainingIgnoreCase(username);
    }
}
