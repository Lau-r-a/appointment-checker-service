package com.appointmentchecker.service.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
public class Notification {

    @Id
    public String id;
    public User user;
}
