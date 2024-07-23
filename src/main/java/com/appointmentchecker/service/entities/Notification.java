package com.appointmentchecker.service.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@AllArgsConstructor
public class Notification {

    @Id
    public String id;
    @DocumentReference
    public User user;
}
