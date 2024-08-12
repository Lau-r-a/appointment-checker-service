package com.appointmentchecker.service;

import com.appointmentchecker.service.drlib.DrLibController;
import com.appointmentchecker.service.drlib.DrLibParams;
import com.appointmentchecker.service.entities.Notification;
import com.appointmentchecker.service.entities.NotificationMapping;
import com.appointmentchecker.service.entities.User;
import com.appointmentchecker.service.facade.NotificationFacade;
import com.appointmentchecker.service.facade.UserFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@SecurityRequirement(name = "security_auth")
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    DrLibController drLibController;
    @Autowired
    NotificationFacade notificationFacade;
    @Autowired
    UserFacade userFacade;

    @GetMapping("/")
    public String index() throws JsonProcessingException {
        return Boolean.toString(drLibController.isAvailable(2208180, 352260, 138926, "public", false, 5, new Date()));
    }

    @PostMapping("/checkAppointment")
    @ResponseStatus(HttpStatus.CREATED)
    public String checkAppointment(@RequestBody DrLibParams drLibParams) {
        return Boolean.toString(drLibController.isAvailable(drLibParams));
    }

    @PutMapping("/user")
    public User createUser(@RequestParam String userId) {
        return userFacade.createUser(userId);
    }

    @PutMapping("/notification")
    public Notification createNotification(@RequestBody DrLibParams drLibParams, @RequestParam String userId) {
        return notificationFacade.createNotification(drLibParams, userFacade.getUserById(userId));
    }

   @GetMapping("/notifications")
   public List<NotificationMapping> getNotifications(@RequestParam String userId) {
        return notificationFacade.getNotificationMappings(userFacade.getUserById(userId));
   }

   @DeleteMapping("/notification")
    public void deleteNotifications(@RequestParam String notificationId) {
        notificationFacade.deleteNotification(notificationId);
   }
}
