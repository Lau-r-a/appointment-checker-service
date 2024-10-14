package com.appointmentchecker.service.facade;

import com.appointmentchecker.service.providers.ProviderParams;
import com.appointmentchecker.service.entities.Notification;
import com.appointmentchecker.service.entities.NotificationDto;
import com.appointmentchecker.service.entities.NotificationTarget;
import com.appointmentchecker.service.entities.User;
import com.appointmentchecker.service.repositories.NotificationRepository;
import com.appointmentchecker.service.repositories.NotificationTargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class NotificationFacade {

    @Autowired
    NotificationTargetRepository notificationTargetRepository;
    @Autowired
    NotificationRepository notificationRepository;


    public Notification createNotification(ProviderParams<?> params, User user) {

        Notification notification = new Notification(UUID.randomUUID().toString(), user);
        NotificationTarget target = notificationTargetRepository.findByParams(params);
        if (target == null) {
            List<Notification> notificationList = new ArrayList<>();
            notificationList.add(notification);
            target = new NotificationTarget(params, notificationList);
        } else {
            List<Notification> notificationList = target.getNotificationList();
            notificationList.add(notification);
        }

        notificationRepository.save(notification);
        notificationTargetRepository.save(target);

        return notification;
    }

    public List<NotificationDto> getNotificationMappings(User user) {
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        List<Notification> notificationList = notificationRepository.findByUser(user);

        for(Notification notification : notificationList) {
            NotificationTarget notificationTarget = notificationTargetRepository.findByNotificationListContaining(notification);
            if (notificationTarget != null ) {
                notificationDtoList.add(new NotificationDto(notification, notificationTarget.getParams()));
            }
        }

        return notificationDtoList;
    }

    public void deleteNotification(String notificationId) {
        Notification notification = getNotification(notificationId);
        NotificationTarget notificationTarget = notificationTargetRepository.findByNotificationListContaining(notification);

        //Todo: this is not actually removing anything
        List<Notification> targetList = notificationTarget.getNotificationList();
        targetList.remove(notification);
        notificationTarget.setNotificationList(targetList);

        notificationTargetRepository.save(notificationTarget);
        notificationRepository.delete(notification);
    }

    public Notification getNotification(String notificationId) {
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        if (notification.isEmpty()) {
            throw new IllegalArgumentException("Notification can not be found!");
        }
        return notification.get();
    }
}
