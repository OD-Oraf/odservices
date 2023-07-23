package com.odoraf.fraud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FraudCheckService {

    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

    public boolean isFraudulentCustomer(String email) {
        /*
            If fraudCheck = true, email was registered as fraudster. If registered as customer,
            the customer service would throw error for duplicate email address so the request would never make it to

            If fraudCheck = false, email is not yet register as a customer nor a fraudster so
            we can go ahead and register

         */
        boolean fraudCheck = fraudCheckHistoryRepository.existsByEmail(email);
        if (fraudCheck) {
            // This check is redundant but keeping in case duplicate email restriction is removed
            FraudCheckHistory fraudulentCustomer = fraudCheckHistoryRepository.findByEmail(email);
            if (fraudulentCustomer.getIsFraudster()) {
                return true;
            }
        }

        createValidCustomer(email);
        return false;
    }

    public void createFraudster(FraudRegistrationRequest request) {
        boolean isFraudEmail = fraudCheckHistoryRepository.existsByEmail(request.email());
        if (isFraudEmail) {
            throw new RuntimeException("This email has already been registered for fraud");
        } else {
            fraudCheckHistoryRepository.save(
                    FraudCheckHistory.builder()
                            .isFraudster(true)
                            .createdAt(LocalDateTime.now())
                            .email(request.email())
                            .build()
            );
        }
    }

    public void createValidCustomer(String email) {
        fraudCheckHistoryRepository.save(
                FraudCheckHistory.builder()
                        .isFraudster(false)
                        .createdAt(LocalDateTime.now())
                        .email(email)
                        .build()
        );
    }

}
