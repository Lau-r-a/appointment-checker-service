package com.appointmentchecker.service.drlib;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @AllArgsConstructor
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

    @Schema(example = "5", required = true)
    private int limt;

    @Schema(example = "10-09-2024", required = true)
    private Date startDate;
}
