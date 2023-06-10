package com.odoraf.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

// Scan packages needed to inject producer
@SpringBootApplication(
        scanBasePackages = {
            "com.odoraf.customer",
            "com.odoraf.amqp"
        }
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.odoraf.clients"
)
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}