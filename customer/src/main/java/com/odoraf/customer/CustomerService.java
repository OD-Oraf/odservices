package com.odoraf.customer;

import com.odoraf.amqp.RabbitMQMessageProducer;
import com.odoraf.clients.fraud.FraudCheckResponse;
import com.odoraf.clients.fraud.FraudClient;
import com.odoraf.clients.notification.NotificationClient;
import com.odoraf.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;
    private RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // TODO: Check if email valid
        // TODO: Check if email not taken
        // Save customer to DB
        customerRepository.saveAndFlush(customer);

        // Send request to service for fraud check
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        // Throw error if customer is fraudulent
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

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
