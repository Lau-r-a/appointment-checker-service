package com.appointmentchecker.service.entities.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(value = { "user" })
public class Notification {

    @Id
    public String id;
    public String name;
    public String description;
    @DocumentReference
    public User user;
}
