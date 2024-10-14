package com.appointmentchecker.service.repositories;

import com.appointmentchecker.service.providers.ProviderParams;
import com.appointmentchecker.service.entities.Notification;
import com.appointmentchecker.service.entities.NotificationTarget;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationTargetRepository extends MongoRepository<NotificationTarget, String> {
    NotificationTarget findByParams(ProviderParams<?> providerParams);
    NotificationTarget findByNotificationListContaining(Notification notificationList);
}
