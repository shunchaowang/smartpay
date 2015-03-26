package com.lambo.smartpay.ecs.config;

import com.lambo.smartpay.core.persistence.entity.User;
import com.lambo.smartpay.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Create a customer UserDetailsService to inject to spring context.
 * Since our user role schema is not following spring security poc, we need to
 * create the customer service to do the authentication.
 * Created by swang on 3/12/2015.
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found.");
        }
        return new SecurityUser(user);
    }
}


