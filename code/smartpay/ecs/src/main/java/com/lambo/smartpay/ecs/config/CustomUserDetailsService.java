package com.lambo.smartpay.ecs.config;

import com.lambo.smartpay.core.persistence.entity.User;
import com.lambo.smartpay.core.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Login username: " + username);
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("Username is blank.");
        }
        String[] merUserStr = StringUtils.split(username, "/");
        if (merUserStr.length != 2) {
            throw new UsernameNotFoundException("Username format is illegal.");
        }

        User user = userService.findUserWithMerchantByUsername(merUserStr[1], merUserStr[0]);
        if (user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found.");
        }
        return new SecurityUser(user);
    }
}


