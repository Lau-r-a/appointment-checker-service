package com.appointmentchecker.service.discord;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscordUserResponse {
    public String id;
}
