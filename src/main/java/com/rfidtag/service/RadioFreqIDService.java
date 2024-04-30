package com.rfidtag.service;

import com.rfidtag.dtos.RFIDTagDto;

import java.util.List;

/**
 * The interface Radio Frequency Identification service
 */
public interface RadioFreqIDService {

    /**
     * @param epc      a unique code for identifying individual items
     * @param tagId    a unique identifier for the RFID tag itself
     * @param location the specific location within the site where the tag was scanned (ex: Warehouse A)
     * @param siteName the name of the exact location where the RFID reader is installed (ex: Aisle 1, Shelf 3)
     * @return list of RFIDTagDto
     */
    List<RFIDTagDto> getRadioFreqIDs(String epc, String tagId, String location, String siteName);


    RFIDTagDto persistRadioFreqID(RFIDTagDto rfidTagDto);
}
