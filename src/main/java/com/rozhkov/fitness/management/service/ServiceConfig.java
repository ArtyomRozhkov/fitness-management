package com.rozhkov.fitness.management.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public FitnessService fitnessService() {
        return new FitnessService();
    }
}
