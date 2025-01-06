package com.appointmentchecker.service.api;

import com.appointmentchecker.service.discord.auth.DiscordController;
import com.appointmentchecker.service.discord.auth.LoginResponse;
import com.appointmentchecker.service.entities.dto.CreateNotificationDto;
import com.appointmentchecker.service.providers.ProviderData;
import com.appointmentchecker.service.providers.Providers;
import com.appointmentchecker.service.providers.drlib.DrLibController;
import com.appointmentchecker.service.providers.drlib.DrLibInfoResponse;
import com.appointmentchecker.service.providers.drlib.DrLibParams;
import com.appointmentchecker.service.entities.model.Notification;
import com.appointmentchecker.service.entities.dto.NotificationDto;
import com.appointmentchecker.service.facade.NotificationFacade;
import com.appointmentchecker.service.facade.UserFacade;
import io.swagger.v3.oas.annotations.Operation;
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
    @Autowired
    DiscordController discordController;

    @PostMapping("/checkAppointment")
    @ResponseStatus(HttpStatus.CREATED)
    public String checkAppointment(@RequestBody DrLibParams drLibParams) {
        return Boolean.toString(drLibController.isAvailable(drLibParams));
    }

    @Operation(summary = "Create a new notification, one of either URL or DrLibParams have to be set.")
    @PostMapping("/notification")
    public NotificationDto createNotification(@RequestBody CreateNotificationDto createNotificationDto, Principal principal) {

        DrLibParams drLibParams;
        String name;

        if (createNotificationDto.getDrLibParams() == null && createNotificationDto.getUrl() != null) {
            DrLibInfoResponse infoResponse = drLibController.requestDrLibInfoFromPublicUrl(createNotificationDto.getUrl());
            drLibParams = drLibController.filterDrLibParamsFromResponse(createNotificationDto.getUrl(), infoResponse);
            name = infoResponse.getName();
        } else if (createNotificationDto.getDrLibParams() != null) {
            drLibParams = createNotificationDto.getDrLibParams();
            name = createNotificationDto.getName();
        } else {
            throw new IllegalArgumentException();
        }

        Notification notification = notificationFacade.createNotification(
                new ProviderData<>(drLibParams, Providers.DRLIB),
                userFacade.getUserById(principal.getName()),
                name,
                createNotificationDto.getDescription()
        );

        return new NotificationDto(notification, new ProviderData<>(drLibParams, Providers.DRLIB));
    }

    @GetMapping("/notifications")
    public List<NotificationDto> getNotifications(Principal principal) {
        return notificationFacade.getNotificationMappings(userFacade.getUserById(principal.getName()));
    }

    @DeleteMapping("/notification")
    public void deleteNotifications(@RequestParam String notificationId) {
        notificationFacade.deleteNotification(notificationId);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestParam String code, @RequestParam String redirect_uri) {
        return discordController.performLoginRequest(code, redirect_uri);
    }
}
