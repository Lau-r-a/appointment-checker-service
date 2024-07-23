package com.appointmentchecker.service;

import com.appointmentchecker.service.drlib.DrLibController;
import com.appointmentchecker.service.drlib.DrLibParams;
import com.appointmentchecker.service.entities.Notification;
import com.appointmentchecker.service.entities.NotificationTarget;
import com.appointmentchecker.service.entities.User;
import com.appointmentchecker.service.repositories.NotificationTargetRepository;
import com.appointmentchecker.service.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    DrLibController drLibController;
    @Autowired
    NotificationTargetRepository notificationTargetRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String index() throws JsonProcessingException {
        return Boolean.toString(drLibController.isAvailable(2208180, 352260, 138926, "public", false, 5, new Date()));
    }

    @PostMapping("/checkAppointment")
    @ResponseStatus(HttpStatus.CREATED)
    public String checkAppointment(@RequestBody DrLibParams drLibParams) {
        return Boolean.toString(drLibController.isAvailable(drLibParams));
    }

    @PutMapping("/createUser")
    public User createUser(@RequestParam String userId) {
        User user = new User(userId, null);
        return userRepository.save(user);
    }

    @PutMapping("/createNotification")
    public Notification createNotification(@RequestBody DrLibParams drLibParams, @RequestParam String userId) {

        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new IllegalArgumentException("User invalid");
        }

        Notification notification = new Notification(UUID.randomUUID().toString(), user.get());
        NotificationTarget target = notificationTargetRepository.findByDrLibParams(drLibParams);
        if (target == null) {
            List<Notification> notificationList = new ArrayList<>();
            notificationList.add(notification);
            target = new NotificationTarget(drLibParams, notificationList);
        } else {
            List<Notification> notificationList = target.getNotificationList();
            notificationList.add(notification);
        }
        notificationTargetRepository.save(target);
        return notification;
    }

}
