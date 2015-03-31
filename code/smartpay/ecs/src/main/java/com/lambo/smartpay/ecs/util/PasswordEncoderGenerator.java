package com.lambo.smartpay.ecs.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by swang on 3/12/2015.
 */
public class PasswordEncoderGenerator {

    public static void main(String[] args) {
        // generate bcrypt password for admin
        String password = "admin";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);

        System.out.println("BCrypted password: " + hashedPassword);
    }
}
