package com.appointmentchecker.service.api;

import com.appointmentchecker.service.providers.ProviderData;
import com.appointmentchecker.service.providers.Providers;
import com.appointmentchecker.service.providers.drlib.DrLibController;
import com.appointmentchecker.service.providers.drlib.DrLibParams;
import com.appointmentchecker.service.entities.Notification;
import com.appointmentchecker.service.entities.NotificationDto;
import com.appointmentchecker.service.facade.NotificationFacade;
import com.appointmentchecker.service.facade.UserFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@SecurityRequirement(name = "Discord Bearer Authentication")
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

    @PutMapping("/notification")
    public Notification createNotification(@RequestBody DrLibParams drLibParams, @RequestBody String description, Principal principal) {
        return notificationFacade.createNotification(new ProviderData<>(drLibParams, Providers.DRLIB) {
        }, userFacade.getUserById(principal.getName()), description);
    }

   @GetMapping("/notifications")
   public List<NotificationDto> getNotifications(Principal principal) {
        return notificationFacade.getNotificationMappings(userFacade.getUserById(principal.getName()));
   }

   @DeleteMapping("/notification")
    public void deleteNotifications(@RequestParam String notificationId) {
        notificationFacade.deleteNotification(notificationId);
   }
}
