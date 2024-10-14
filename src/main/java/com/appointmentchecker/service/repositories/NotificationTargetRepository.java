package com.appointmentchecker.service.repositories;

import com.appointmentchecker.service.providers.ProviderData;
import com.appointmentchecker.service.entities.Notification;
import com.appointmentchecker.service.entities.NotificationTarget;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationTargetRepository extends MongoRepository<NotificationTarget, String> {
    NotificationTarget findByProviderData(ProviderData<?> providerData);
    NotificationTarget findByNotificationListContaining(Notification notificationList);
}
