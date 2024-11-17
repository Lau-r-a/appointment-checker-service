package com.appointmentchecker.service.discord.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class DiscordController {

    @Value("${spring.security.oauth2.client.registration.discord.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.discord.client-secret}")
    private String clientSecret;

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

    public LoginResponse performLoginRequest (String code, String redirect_uri) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();
        map.put("redirect_uri", redirect_uri);
        map.put("code", code);
        map.put("client_id", clientId);
        map.put("client_secret", clientSecret);

        return restTemplate
                .getForObject("https://discordapp.com/api/oauth2/token?grant_type=authorization_code&code={code}&client_id={client_id}&client_secret={client_secret}&redirect_uri={redirect_uri}", LoginResponse.class, map);
    }

    public String getIdByToken(String token) {
        return getDiscordUser(token).id;
    }
}
