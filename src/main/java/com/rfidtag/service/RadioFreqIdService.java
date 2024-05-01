package com.rfidtag.service;

import com.rfidtag.dtos.RFIDTagDto;

import java.util.List;

/**
 * The interface Radio Frequency Identification service
 */
public interface RadioFreqIdService {

    /**
     * Get All radio frequency identifications associated to epc OR tagId OR location OR siteName
     *
     * @param rfidTagDto the radio frequency tag identification request
     * @return list of RFIDTagDto
     */
    List<RFIDTagDto> getRadioFreqIDs(RFIDTagDto rfidTagDto);


    /**
     * Persist radio frequency identification
     *
     * @param rfidTagDto the rfid tag dto to be created
     * @return RFIDTagDto
     */
    RFIDTagDto persistRadioFreqID(RFIDTagDto rfidTagDto);

    /**
     * Update radio frequency identification according to the request body rfidTagDto
     *
     * @param rfidTagDto the rfid tag dto to be created
     * @return RFIDTagDto
     */
    RFIDTagDto updateRadioFreqID(String id, RFIDTagDto rfidTagDto);

    /**
     * Delete RFID associated with id
     *
     * @param id the id of RFID
     */
    void deleteRadioFreqId(String id);
}
