package com.appointmentchecker.service.discord.bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Component
public class DiscordBotController {
    @Autowired
    JDA jda;

    private User getUser(String userId) {
        try {
            return jda.retrieveUserById(userId).submit().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPrivateMessage(String userId, String content) {
        getUser(userId).openPrivateChannel()
            .flatMap(channel -> channel.sendMessage(content))
            .queue();
    }

    public void sendPrivateMessageEmbeds(String userId, String description) {
        getUser(userId).openPrivateChannel()
                .flatMap(channel -> channel.sendMessageEmbeds(buildEmbedMessage(description)))
                .queue();
    }

    private MessageEmbed buildEmbedMessage(String description) {
        return new EmbedBuilder()
            .setAuthor("Appointment-Checker")
            .setDescription(description)
            .setTitle("Appointment available!")
            .setColor(new Color(153, 67, 179))
            .setFooter("test footer")
            .setThumbnail("https://www.bgw-online.de/resource/blob/73320/6426f22cbe30b822e4b623d3995a40c0/d-arzt-aerztin-tif-original-data.jpg")
            .build();
    }
}