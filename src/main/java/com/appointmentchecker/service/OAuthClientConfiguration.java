package com.appointmentchecker.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class OAuthClientConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/swagger-ui/*").permitAll()
                        .requestMatchers("/api-docs/*").permitAll()
                        .requestMatchers("/api-docs").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login((withDefaults()));
        return http.build();
    }

}