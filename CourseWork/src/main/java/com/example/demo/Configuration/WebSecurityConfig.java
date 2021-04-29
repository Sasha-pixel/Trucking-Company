package com.example.demo.Configuration;

import com.example.demo.Roles.Role;
import com.example.demo.Services.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

/**
 * Конфигурация Security
 *
 * @author kanenkovaa
 * @version 0.1
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DetailsService service;

    /**
     * Настройка доступа страниц
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/registration").permitAll()
                    .antMatchers("/registrationAction").permitAll()
                    .antMatchers("/end").permitAll()
                .antMatchers("/admin/**").hasAuthority(String.valueOf(Role.ADMIN))
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login").permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .permitAll();
    }

    /**
     * Метод авторизации
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(passwordEncoder())
//                .usersByUsernameQuery("select username, password, roles from user8 where username = ?")
//                .authoritiesByUsernameQuery("select u.username, ur.roles from user8 u inner join user_role8 ur on u.id = ur.user_id where u.username=?");
        auth.userDetailsService(service).passwordEncoder(passwordEncoder());
    }

    /**
     * Создание бина BCryptPasswordEncoder
     *
     * @return a {@link org.springframework.security.crypto.password.PasswordEncoder} object.
     */
    @Bean("pass")
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
