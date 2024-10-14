package com.appointmentchecker.service.entities;

import com.appointmentchecker.service.providers.ProviderParams;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class NotificationTarget {

    @Id
    public ProviderParams<?> params;
    @DocumentReference
    public List<Notification> notificationList;
}
