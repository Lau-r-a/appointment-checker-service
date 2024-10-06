package com.appointmentchecker.service.entities;

import com.appointmentchecker.service.drlib.DrLibParams;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationDto {

    public String id;
    DrLibParams drLibParams;

    public NotificationDto (Notification notification, DrLibParams drLibParams) {
        this.id = notification.getId();
        this.drLibParams = drLibParams;
    }
}
