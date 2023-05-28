package com.odoraf.notifications;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;
// Interact with notification DB
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
