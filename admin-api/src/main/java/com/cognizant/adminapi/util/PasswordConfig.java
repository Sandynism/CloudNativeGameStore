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

//Admin - admin || $2a$10$ATyLYRfzbu/daJ.HY0QkgO2L/qzS/a6k6XtMgpfJMaLFHr4/d4Zp6
//Manager - manager || $2a$10$m8F6rV7YbwpFdXefSRmuD.SX/hgf6kf2NU98Tmie9cOgUJR962Vl.
//TeamLead - teamlead || $2a$10$jm1AOMnNrNjJugc1squzruzFq8oGDzyB94HpA8zmLzQUacbXWdOWW
//Employee - employee || $2a$10$bexCKdUGS/RAZI8SAlnEPeY6wIBq/w9Pu7TrNnXJZQWJQ6zC9mtWy

