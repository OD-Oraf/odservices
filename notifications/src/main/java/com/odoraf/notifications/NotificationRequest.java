package com.odoraf.notifications;

import java.time.LocalDate;
import java.time.LocalDateTime;

// Template for notification request
public record NotificationRequest(
        Integer toCustomerId,
        String toCustomerEmail,
        String message
) {
}
