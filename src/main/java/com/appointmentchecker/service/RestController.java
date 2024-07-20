package com.appointmentchecker.service;

import com.appointmentchecker.service.drlib.DrLibRequestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;


@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/")
    @Bean
    public String index(DrLibRequestController drlibcontroller) throws JsonProcessingException {
        return Boolean.toString(drlibcontroller.isAvailable(2208180, 352260, 138926, "public", false, 5, new Date()));
    }
}
