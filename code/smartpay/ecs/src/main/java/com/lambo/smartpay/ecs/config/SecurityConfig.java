package com.lambo.smartpay.ecs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders
        .AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration
        .EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration
        .WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by swang on 3/12/2015.
 * We need to have EnableGlabalMethodSecurity annotation to use secured annotation on
 * classes and methods. Make sure SecurityConfig needs to be loaded by We App Initializer for
 * Secured annotation only takes effect on the current servlet context. So We need to have
 * SecurityConfig in dispatcher servlet context instead of spring application context. This is
 * very important!
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().
                antMatchers("/assets/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // ROLEs must not have ROLE prefix
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/login/form**", "/register", "/logout").permitAll() // #1
                //.antMatchers("/admin", "/admin/**").hasRole("ADMIN") // #2
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login/form")
                .loginProcessingUrl("/login")
                .failureUrl("/login/form?error")
                .permitAll() // #3
                .and()
                .exceptionHandling().accessDeniedPage("/403") // #4 denied, mapped in Controller
        ;
    }

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
