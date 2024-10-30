package com.appointmentchecker.service.entities;

import com.appointmentchecker.service.providers.drlib.DrLibInfoResponse;
import com.appointmentchecker.service.providers.drlib.DrLibParams;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class DrLibInfoDto {
    DrLibInfoResponse drLibInfoResponse;
    DrLibParams drLibParams;
}
