package com.appointmentchecker.service.entities;

import com.appointmentchecker.service.drlib.DrLibParams;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationMapping {
    Notification notification;
    DrLibParams drLibParams;
}
