package com.rfidtag.controller;

import com.rfidtag.dtos.RFIDTagDto;
import com.rfidtag.service.RadioFreqIDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Radio Frequency Identification (RFID) controller
 *
 * @author Reem Gharib
 */
@RequiredArgsConstructor
@RequestMapping("radio-freq-identification")
@RestController
public class RadioFreqIDController {

    private final RadioFreqIDService radioFreqIDService;

    @GetMapping
    public ResponseEntity<List<RFIDTagDto>> getAllRadioFreqIDs(@RequestParam(required = false) String epc,
                                                               @RequestParam(required = false) String tagId,
                                                               @RequestParam(required = false) String location,
                                                               @RequestParam(required = false) String siteName) {

        return new ResponseEntity<>(this.radioFreqIDService.getRadioFreqIDs(epc, tagId, location, siteName),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RFIDTagDto> createRadioFreqID(@RequestBody RFIDTagDto rfidTagDto) {

        return new ResponseEntity<>(this.radioFreqIDService.persistRadioFreqID(rfidTagDto), HttpStatus.CREATED);
    }

}
