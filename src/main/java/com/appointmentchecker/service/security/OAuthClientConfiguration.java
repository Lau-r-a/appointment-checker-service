package com.appointmentchecker.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class OAuthClientConfiguration {

    @Autowired
    TokenValidationFilter tokenValidationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .addFilterAfter(tokenValidationFilter, BasicAuthenticationFilter.class)
            .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/swagger-ui/*").permitAll()
                    .requestMatchers("/api-docs/*").permitAll()
                    .requestMatchers("/api-docs").permitAll()
                    .anyRequest().authenticated()
            )
            .csrf(AbstractHttpConfigurer::disable)
            .build();
    }
}