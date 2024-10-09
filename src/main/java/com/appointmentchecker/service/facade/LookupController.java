package com.appointmentchecker.service.facade;

import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LookupController {

    @Autowired
    JDA jda;

    Logger logger = LoggerFactory.getLogger(LookupController.class);

    @Scheduled(fixedDelay = 10 * 60 * 1000)
    public void lookupAndNotify() {
        logger.info("Fixed delay task - " + System.currentTimeMillis() / 1000);
    }
}
