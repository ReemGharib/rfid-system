package com.rfidtag.dtos;


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

    private String id;

    private String siteName;

    private String epc;

    private String tagId;

    private String location;

    private String rssi;

    private LocalDate date;
}
