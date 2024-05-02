package com.rfidtag.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

/**
 * @author Reem Gh.
 */
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RFIDTagDto {

    @Schema(example = "123456")
    private String id;

    @Schema(example = "Warehouse A")
    private String siteName;

    @Schema(example = "EPC66666")
    private String epc;

    @Schema(example = "TAG898989")
    private String tagId;

    @Schema(example = "Aisle 1, Shelf 2")
    private String location;

    @Schema(example = "-60dBm")
    private String rssi;

    @Schema(example = "2024-5-2")
    private LocalDate date;
}
