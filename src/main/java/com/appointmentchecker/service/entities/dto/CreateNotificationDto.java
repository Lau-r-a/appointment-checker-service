package com.appointmentchecker.service.entities.dto;

import com.appointmentchecker.service.providers.drlib.DrLibParams;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateNotificationDto {

    @Schema(example = "https://www.doctolib.de/allgemeinmedizin/regensburg/nils-lenuweit/booking/availabilities?specialityId=1286&telehealth=false&placeId=practice-598656&insuranceSectorEnabled=true&insuranceSector=private&isNewPatient=true&isNewPatientBlocked=false&motiveIds%5B%5D=12624653&pid=practice-598656&bookingFunnelSource=profile")
    private String url;
    @Schema(example = "Dr SoUndSo")
    private String name;
    @Schema(example = "Example Description")
    private String description;
    private DrLibParams drLibParams;

}
