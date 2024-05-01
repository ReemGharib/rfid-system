package com.rfidtag.dtos;


import lombok.*;

/**
 * @author Reem Gharib
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ErrorResponse {

    private String reason;

    private String code;

    private String description;

}