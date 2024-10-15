package com.appointmentchecker.service.entities;

import com.appointmentchecker.service.providers.ProviderData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationDto {

    public String id;
    public String description;
    ProviderData<?> providerData;

    public NotificationDto (Notification notification, ProviderData<?> providerData) {
        this.id = notification.getId();
        this.description = notification.getDescription();
        this.providerData = providerData;
    }
}
