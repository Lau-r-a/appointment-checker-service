package com.appointmentchecker.service.providers.drlib;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DrLibResponse {
    public int total;
}
