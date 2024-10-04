package com.appointmentchecker.service.security;

import com.appointmentchecker.service.discord.DiscordController;
import com.appointmentchecker.service.facade.UserFacade;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.io.IOException;

@Component
public class TokenValidationFilter extends OncePerRequestFilter {

    @Autowired
    private DiscordController discordController;

    @Autowired
    private UserFacade userFacade;

    Logger logger = LoggerFactory.getLogger(TokenValidationFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ") && !authHeader.substring(7).isBlank()) {
            String accessToken = authHeader.substring(7);
            try {
                String userId = getUserIdIfValidToken(accessToken);
                userFacade.createOrUpdateUser(userId, accessToken);

                Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userId, accessToken, null);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (InvalidTokenException e) {
                logger.error("Can not validate token: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getUserIdIfValidToken(String accessToken) {

        String userId = userFacade.getUserIdByToken(accessToken);

        if (userId == null) {
            userId = discordController.getIdByToken(accessToken);
        }

        logger.info("User access with id {}.", userId);
        return userId;
    }
}