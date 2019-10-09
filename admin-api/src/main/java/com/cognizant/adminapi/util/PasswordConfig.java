package com.cognizant.adminapi.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordConfig {

    public static void main(String[] args) {
        PasswordEncoder enc = new BCryptPasswordEncoder();

        String password = "password";

        String encodedPassword = enc.encode(password);

        System.out.println(encodedPassword);
    }
}



