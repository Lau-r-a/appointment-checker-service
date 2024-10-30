package com.appointmentchecker.service.providers.drlib;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class DrLibAgenda {
    int id;
    boolean bookingDisabled;
    List<Integer> visitMotiveIds;
    int practitionerId;
    int practiceId;
}
