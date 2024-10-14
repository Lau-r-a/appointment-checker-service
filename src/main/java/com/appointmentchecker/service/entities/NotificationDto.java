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
    ProviderData<?> providerData;

    public NotificationDto (Notification notification, ProviderData<?> providerData) {
        this.id = notification.getId();
        this.providerData = providerData;
    }
}
