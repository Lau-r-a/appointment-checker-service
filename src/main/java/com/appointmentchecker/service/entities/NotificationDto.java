package com.appointmentchecker.service.entities;

import com.appointmentchecker.service.providers.ProviderParams;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationDto {

    public String id;
    ProviderParams<?> params;

    public NotificationDto (Notification notification, ProviderParams<?> params) {
        this.id = notification.getId();
        this.params = params;
    }
}
