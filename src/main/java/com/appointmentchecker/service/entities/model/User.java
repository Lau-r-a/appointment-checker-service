package com.appointmentchecker.service.entities.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
public class User {

    @Id
    public String id;
    public String accessToken;
}
