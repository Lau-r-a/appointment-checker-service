package com.appointmentchecker.service;

import com.appointmentchecker.service.drlib.DrLibController;
import com.appointmentchecker.service.drlib.DrLibParams;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    DrLibController drLibController;

    @GetMapping("/")
    public String index() throws JsonProcessingException {
        return Boolean.toString(drLibController.isAvailable(2208180, 352260, 138926, "public", false, 5, new Date()));
    }

    @PostMapping("/checkAppointment")
    @ResponseStatus(HttpStatus.CREATED)
    public String checkAppointment(@RequestBody DrLibParams drLibParams) {
        return Boolean.toString(drLibController.isAvailable(drLibParams));
    }

}
