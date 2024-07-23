package com.appointmentchecker.service.repositories;

import com.appointmentchecker.service.drlib.DrLibParams;
import com.appointmentchecker.service.entities.Notification;
import com.appointmentchecker.service.entities.NotificationTarget;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationTargetRepository extends MongoRepository<NotificationTarget, String> {
    public NotificationTarget findByDrLibParams(DrLibParams drLibParams);
    public NotificationTarget findByNotificationListContaining(Notification notificationList);

}
