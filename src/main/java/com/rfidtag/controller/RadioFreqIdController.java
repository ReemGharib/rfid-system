package com.rfidtag.controller;

import com.rfidtag.dtos.RFIDTagDto;
import com.rfidtag.service.RadioFreqIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

/**
 * Radio Frequency Identification (RFID) controller
 *
 * @author Reem Gharib
 */
@RequiredArgsConstructor
@RequestMapping("radio-freq-identification")
@RestController
public class RadioFreqIdController {

    private final RadioFreqIdService radioFreqIdService;

    @GetMapping
    public ResponseEntity<List<RFIDTagDto>> getAllRadioFreqIds(@RequestParam(required = false) String epc,
                                                               @RequestParam(required = false) String tagId,
                                                               @RequestParam(required = false) String location,
                                                               @RequestParam(required = false) String siteName,
                                                               @RequestParam(required = false) String rssi,
                                                               @RequestParam(required = false) LocalDate date) {
        RFIDTagDto rfidTagDto = RFIDTagDto.builder()
                .siteName(siteName)
                .epc(epc)
                .tagId(tagId)
                .location(location)
                .rssi(rssi)
                .date(date)
                .build();

        return new ResponseEntity<>(this.radioFreqIdService.getRadioFreqIDs(rfidTagDto),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RFIDTagDto> createRadioFreqId(@RequestBody RFIDTagDto rfidTagDto) {

        return new ResponseEntity<>(this.radioFreqIdService.persistRadioFreqID(rfidTagDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RFIDTagDto> updateRadioFreqId(@PathVariable("id") String id,
                                                        @RequestBody RFIDTagDto rfidTagDto) {

        return new ResponseEntity<>(this.radioFreqIdService.updateRadioFreqID(id, rfidTagDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRadioFreqId(@PathVariable("id") String id) {

        this.radioFreqIdService.deleteRadioFreqId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
