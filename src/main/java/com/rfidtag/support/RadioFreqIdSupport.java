package com.rfidtag.support;

import com.rfidtag.dtos.RFIDTagDto;
import com.rfidtag.model.RFIDTag;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

/**
 * Radio frequency identification support
 */
@Component
public class RadioFreqIdSupport {

    /**
     * Validate rfid tag
     *
     * @param rfidTagDto the
     */
    public void validateRFIDTag(RFIDTagDto rfidTagDto) {

        Validate.notBlank(rfidTagDto.getTagId(), "tagId is required");
        Validate.notBlank(rfidTagDto.getEpc(), "epc is required");
        Validate.notBlank(rfidTagDto.getSiteName(), "siteName is required");
        Validate.notBlank(rfidTagDto.getLocation(), "location is required");

    }

    /**
     * Populate rfid tag dto
     *
     * @param rfid the rfid tag entity
     */
    public RFIDTagDto populateRFIDTagDto(RFIDTag rfid) {

        return RFIDTagDto.builder()
                .id(rfid.getId().toString())
                .siteName(rfid.getSiteName())
                .epc(rfid.getEpc())
                .tagId(rfid.getTagId())
                .location(rfid.getLocation())
                .rssi(rfid.getRssi())
                .date(rfid.getDate())
                .build();
    }
}
