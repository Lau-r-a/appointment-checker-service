package com.appointmentchecker.service;

import com.appointmentchecker.service.discord.bot.DiscordBotController;
import com.appointmentchecker.service.entities.model.Notification;
import com.appointmentchecker.service.entities.model.NotificationTarget;
import com.appointmentchecker.service.facade.NotificationFacade;
import com.appointmentchecker.service.providers.Providers;
import com.appointmentchecker.service.providers.drlib.DrLibController;
import com.appointmentchecker.service.providers.drlib.DrLibParams;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Slf4j
@Component
public class AvailabilityCheckJob {

    @Autowired
    NotificationFacade notificationFacade;

    @Autowired
    DrLibController drLibController;

    @Autowired
    DiscordBotController discordBotController;

    private DrLibParams mapObject(LinkedHashMap<?,?> map) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(map, new TypeReference<>() {
        });
    }

    @Scheduled(fixedDelay = 10 * 60 * 1000)
    public void lookupAndNotify() {
        log.info("Executing Lookup Job - " + System.currentTimeMillis() / 1000);
        for(NotificationTarget target : notificationFacade.getNotificationTargets()) {

            if (target.getProviderData().getProvider() == Providers.DRLIB) {
                DrLibParams params = mapObject((LinkedHashMap<?, ?>) target.getProviderData().getParams());

                if (drLibController.isAvailable(params)) {
                    for(Notification notification : target.getNotificationList()) {

                        log.info("Notify user {} for notification {}", notification.getUser().getId(), notification.getId());

                        discordBotController.sendPrivateMessageEmbeds(
                                notification.getUser().getId(),
                                notification.getDescription()
                        );
                    }
                } else {
                    log.info("Not available");
                }
            }
        }
    }
}
