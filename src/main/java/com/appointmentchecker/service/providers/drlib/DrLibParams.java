package com.appointmentchecker.service.providers.drlib;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DrLibParams {

    @Schema(example = "2208180", required = true)
    private long visitMotiveIds;

    @Schema(example = "352260", required = true)
    private int agendaIds;

    @Schema(example = "138926", required = true)
    private int practiceIds;

    @Schema(example = "public", required = true)
    private String insuranceSector;

    @Schema(example = "false", required = true)
    private boolean telehealth;
}
