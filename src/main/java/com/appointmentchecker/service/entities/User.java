package com.appointmentchecker.service.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class User {

    @Id
    public String id;
    public List<Notification> notificationList;
}
