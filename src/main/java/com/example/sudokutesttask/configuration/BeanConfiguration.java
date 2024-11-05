package com.example.sudokutesttask.configuration;

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

}
