package com.example.demo.Services;

import com.example.demo.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Класс сервиса авторизации
 *
 * @author kanenkovaa
 * @version 0.2
 */
@Service
public class DetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * параметризованный конструктор
     * @param userRepository репозиторий для работы с таблицей пользователей
     */
    public DetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * метод поиска пользователя в таблице по введённому имени  в форме авторизации
     *
     * @param s имя пользователя
     * @return объект пользователя
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }
}
