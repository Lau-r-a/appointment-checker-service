package com.appointmentchecker.service.discord;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class DiscordController {
    private DiscordUserResponse getDiscordUser(String token) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://discord.com/api/v10/users/@me";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "appointment-checker-bot");
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<DiscordUserResponse>() {}).getBody();
    }

    public String getIdByToken(String token) {
        return getDiscordUser(token).id;
    }
}
