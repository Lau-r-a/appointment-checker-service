package com.appointmentchecker.service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(info = @Info(title = "Appointment checker service", description = "Service to check available doctor appointments and create notifications", version = "0.1"))
@SecurityScheme(name = "security_auth", type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(authorizationUrl = "https://discordapp.com/api/oauth2/authorize", tokenUrl = "${spring.security.oauth2.client.provider.discord.token-uri}", scopes = {
                @OAuthScope(name = "identify", description = "identify"),
        })))
@SecurityScheme(
        name = "Discord Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)
public class OpenAPI3Configuration {

}
