package com.appointmentchecker.service;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DiscordBotConfiguration {

    @Value("${property.discord.bot-token}")
    private String token;

    @Bean
    public JDA jda() throws InterruptedException {
        JDA jda =  JDABuilder
                .createDefault(token)
                .setActivity(Activity.listening("User command"))
                .build();

        jda.awaitReady();
        return jda;
    }

}
