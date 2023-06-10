package com.odoraf.notification;

// Template for notification request
public record NotificationRequest(
        Integer toCustomerId,
        String toCustomerEmail,
        String message
) {
}
