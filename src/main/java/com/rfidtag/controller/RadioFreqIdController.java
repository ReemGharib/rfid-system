package com.rfidtag.controller;

import com.rfidtag.dtos.ErrorResponse;
import com.rfidtag.dtos.RFIDTagDto;
import com.rfidtag.service.RadioFreqIdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "RFID returned successfully!",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = RFIDTagDto.class)))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @Operation(summary = "Get All Radio frequency identification tags")
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "RFID Tag created successfully!", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RFIDTagDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Operation(summary = "Create new RFID Tag")
    @PostMapping
    public ResponseEntity<RFIDTagDto> createRadioFreqId(@RequestBody RFIDTagDto rfidTagDto) {

        return new ResponseEntity<>(this.radioFreqIdService.persistRadioFreqID(rfidTagDto), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "RFID Tag updated successfully!"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Operation(summary = "Update an existing RFID Tag, regarding epc and tagId should be different to other entities")
    @PutMapping("/{id}")
    public ResponseEntity<RFIDTagDto> updateRadioFreqId(@PathVariable("id") String id,
                                                        @RequestBody RFIDTagDto rfidTagDto) {

        return new ResponseEntity<>(this.radioFreqIdService.updateRadioFreqID(id, rfidTagDto), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "RFID Tag deleted successfully!"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Operation(summary = "Create new RFID Tag")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRadioFreqId(@PathVariable("id") String id) {

        this.radioFreqIdService.deleteRadioFreqId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
