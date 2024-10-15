package com.appointmentchecker.service.discord.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Component
public class DiscordBotController {
    @Autowired
    JDA jda;

    public void sendPrivateMessage(String userId, String message) {
        User discordUser = null;
        try {
            discordUser = jda.retrieveUserById(userId).submit().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        sendPrivateMessage(Objects.requireNonNull(discordUser), message);
    }

    private void sendPrivateMessage(User user, String content) {
        user.openPrivateChannel()
            .flatMap(channel -> channel.sendMessage(content))
            .queue();
    }
}