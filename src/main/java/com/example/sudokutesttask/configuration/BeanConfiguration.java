package com.example.sudokutesttask.configuration;

import org.springframework.boot.web.server.Cookie;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;
import java.util.Random;

@Configuration
public class BeanConfiguration {

    @Bean
    public Random random() {
        return new SecureRandom();
    }

//    @Bean
//    public Cookie getSessionCookie() {
//        Cookie sessionCookie = new Cookie();
//        sessionCookie.setSecure(false);  // Set to true if using HTTPS
//        sessionCookie.setHttpOnly(false); // Optional, recommended for security
//        sessionCookie.setSameSite(Cookie.SameSite.NONE);  // Use "None" for cross-origin requests
//        return sessionCookie;
//
//    }
}
