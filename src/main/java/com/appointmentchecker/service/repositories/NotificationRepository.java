package com.appointmentchecker.service.repositories;

import com.appointmentchecker.service.entities.Notification;
import com.appointmentchecker.service.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByUser(User user);
}
