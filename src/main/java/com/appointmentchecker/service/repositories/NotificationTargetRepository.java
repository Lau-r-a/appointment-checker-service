package com.appointmentchecker.service.repositories;

import com.appointmentchecker.service.drlib.DrLibParams;
import com.appointmentchecker.service.entities.Notification;
import com.appointmentchecker.service.entities.NotificationTarget;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationTargetRepository extends MongoRepository<NotificationTarget, String> {
    NotificationTarget findByDrLibParams(DrLibParams drLibParams);
    NotificationTarget findByNotificationListContaining(Notification notificationList);
}
