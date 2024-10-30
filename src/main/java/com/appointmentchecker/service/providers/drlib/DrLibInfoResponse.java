package com.appointmentchecker.service.providers.drlib;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@JsonDeserialize(using = DrLibInfoResponseDeserializer.class)
public class DrLibInfoResponse {
    private int id;
    private String name;
    private List<DrLibAgenda> agendaList;
}


