package com.odoraf.notification;

import org.springframework.data.jpa.repository.JpaRepository;

// Interact with notification DB
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
