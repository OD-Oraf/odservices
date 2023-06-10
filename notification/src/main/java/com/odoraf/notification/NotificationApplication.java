package com.odoraf.notification;

import com.odoraf.amqp.RabbitMQMessageProducer;
import com.rabbitmq.client.Command;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
        scanBasePackages = {
                "com.odoraf.notification",
                "com.odoraf.amqp",
        }
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.odoraf.clients"
)
public class NotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(
//        RabbitMQMessageProducer producer,
//        NotificationConfig notificationConfig
//        ) {
//        return args -> {
//            producer.publish(
//                    new Person("Ali", 18),
//                    notificationConfig.getInternalExchange(),
//                    notificationConfig.getInternalNotificationRoutingKey()
//            );
//
//        };
//    }
//
//    record Person(String name, int age) {
//
//    }

}
