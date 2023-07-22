package com.odoraf.customer;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Deprecated
public class CustomerConfig {


    @Bean
    @LoadBalanced // required to hit service endpoint using application name
    public RestTemplate restTemplate() { // Used to connect to other microservice
        return new RestTemplate();
    }
}
