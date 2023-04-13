package com.odoraf.customer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomerConfig {

    @Bean
    public RestTemplate restTemplate() { // Used to connect to other microservice
        return new RestTemplate();
    }
}
