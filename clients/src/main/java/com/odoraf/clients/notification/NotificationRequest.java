package com.odoraf.clients.notification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record NotificationRequest(
        Integer toCustomerId,
        String toCustomerEmail,
        String message
) {
}
