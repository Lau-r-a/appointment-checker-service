package com.appointmentchecker.service.entities;

import com.appointmentchecker.service.drlib.DrLibParams;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class NotificationTarget {

    @Id
    public DrLibParams drLibParams;
    public List<Notification> notificationList;
}
