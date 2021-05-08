package com.example.demo.Configuration;


import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Класс, отвечающий за авторизацию пользователя или создание cookie файлов
 *
 * @author kanenkovaa
 */
public class CustomFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * Создание конфигурации фильтра авторизаци
     * @param url маппинг, при котором срабатывает фильтр
     * @param authenticationManager
     */
    public CustomFilter(String url, AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);

        setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler(){
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

                Cookie passwordCookie = new Cookie("password", request.getParameter("password"));
                Cookie loginCookie = new Cookie("login", request.getParameter("username"));
                passwordCookie.setMaxAge(60*60*24*365);
                loginCookie.setMaxAge(60*60*24*365);
                response.addCookie(passwordCookie);
                response.addCookie(loginCookie);
                super.setDefaultTargetUrl("/main");
                super.onAuthenticationSuccess(request, response, authentication);
            }
        });

        setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(){
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                super.setDefaultFailureUrl("/login?error");
                super.onAuthenticationFailure(request, response, exception);
            }
        });

        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(url, HttpMethod.POST.name()));
    }
}
