package com.odoraf.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

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
@PropertySources({
        @PropertySource("classpath:clients-${spring.profiles.active}.properties")
})
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}