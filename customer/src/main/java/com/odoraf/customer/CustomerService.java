package com.odoraf.customer;

import com.odoraf.amqp.RabbitMQMessageProducer;
import com.odoraf.clients.fraud.FraudCheckResponse;
import com.odoraf.clients.fraud.FraudClient;
import com.odoraf.clients.notification.NotificationClient;
import com.odoraf.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;
    private RabbitMQMessageProducer rabbitMQMessageProducer;

    public static boolean isValidEmail(String emailAddress) {
        String regexPattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
    public void registerCustomer(CustomerRegistrationRequest request) {
        // TODO: Check if email valid
        if (!isValidEmail(request.email())) {
            throw new RuntimeException("Invalid Email");
        }
        // Save customer to DB
        if (customerRepository.existsByEmail(request.email())) {
            throw new RuntimeException("User with that email already exists");
        }

        // Send request to service for fraud check
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(request.email());

        // Throw error if customer is fraudulent
        if (fraudCheckResponse.isFraudster()) {
            throw new RuntimeException("This email has been flagged for fraud");
        }

        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        customerRepository.saveAndFlush(customer);

        // Make async, add to queue
        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to OdServices",
                        customer.getFirstName())
        );

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );

    }
}
