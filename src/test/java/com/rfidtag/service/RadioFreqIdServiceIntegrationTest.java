package com.rfidtag.service;

import com.rfidtag.RfidTagApplication;
import com.rfidtag.dtos.RFIDTagDto;
import com.rfidtag.exception.RadioFreqIdAlreadyExistException;
import com.rfidtag.exception.RadioFreqIdNotFoundException;
import com.rfidtag.model.RFIDTag;
import com.rfidtag.repository.RadioFreqIdRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Radio frequency identification service Integration Tests
 *
 * @author Reem Gharib
 */
@ActiveProfiles({"integration", "test"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RfidTagApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RadioFreqIdServiceIntegrationTest {

    @Autowired
    private RadioFreqIdService radioFreqIdService;

    @Autowired
    private RadioFreqIdRepository radioFreqIdRepository;

    // Entities and DTOs
    private RFIDTagDto rfidTagDto;
    private RFIDTag rfidTag;
    private RFIDTag rfidTag2;

    @BeforeEach
    public void setUp() {

        rfidTagDto = RFIDTagDto.builder()
                .id("1")
                .siteName("Warehouse A")
                .epc("EPC123456789")
                .tagId("TAG987654321")
                .location("Shelf 1, Aisle 2")
                .build();

        rfidTag = RFIDTag.builder()
                .id(1L)
                .siteName("Warehouse A")
                .epc("EPC123456789")
                .tagId("TAG987654321")
                .location("Shelf 1, Aisle 2")
                .rssi("-60dBm")
                .date(LocalDate.now())
                .build();

        rfidTag2 = RFIDTag.builder()
                .id(2L)
                .siteName("Warehouse B")
                .epc("EPC123456789")
                .tagId("TAG333333")
                .location("Shelf 3, Aisle 2")
                .rssi("-60dBm")
                .date(LocalDate.now())
                .build();

        this.radioFreqIdRepository.save(rfidTag);
        this.radioFreqIdRepository.save(rfidTag2);

    }

    @Test
    public void testGetRadioFreqIDs() {

        List<RFIDTagDto> response = this.radioFreqIdService.getRadioFreqIDs(rfidTagDto);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals("1", response.get(0).getId());
        Assertions.assertEquals("2", response.get(1).getId());
    }

    @Test
    public void testPersistRadioFreqID_throwAlreadyExistException() {

        RadioFreqIdAlreadyExistException exception = assertThrows(RadioFreqIdAlreadyExistException.class,
                () -> this.radioFreqIdService.persistRadioFreqID(rfidTagDto));

        Assertions.assertEquals("RFID Tag already exists", exception.getMessage());
    }

    @Test
    public void testPersistRadioFreqID() {

        RFIDTagDto response = this.radioFreqIdService.persistRadioFreqID(RFIDTagDto.builder()
                .siteName("Warehouse B")
                .epc("EPC444444")
                .tagId("TAG444444")
                .location("Shelf 3, Aisle 2")
                .rssi("-60dBm")
                .date(LocalDate.now())
                .build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Warehouse B", response.getSiteName());
        Assertions.assertEquals("EPC444444", response.getEpc());
        Assertions.assertEquals("TAG444444", response.getTagId());
    }

    @Test
    public void testUpdateRadioFreqID_throwRadioFreqIdNotFoundException() {

        RadioFreqIdNotFoundException exception = assertThrows(RadioFreqIdNotFoundException.class,
                () -> this.radioFreqIdService.updateRadioFreqID("5", rfidTagDto));

        Assertions.assertEquals("RFID tag not found!", exception.getMessage());
    }

    @Test
    public void testUpdateRadioFreqID_sameTagId_throwAlreadyExistException() {

        this.radioFreqIdRepository.save(RFIDTag.builder()
                .id(2L)
                .siteName("Warehouse A")
                .epc("EPC33")
                .tagId("TAG987654321")
                .location("Shelf 111, Aisle 2")
                .rssi("-601dBm")
                .date(LocalDate.now())
                .build());

        RFIDTagDto rfidDto = RFIDTagDto.builder()
                .siteName("Warehouse C")
                .tagId("TAG987654321")
                .build();

        RadioFreqIdAlreadyExistException exception = assertThrows(RadioFreqIdAlreadyExistException.class,
                () -> this.radioFreqIdService.updateRadioFreqID("1", rfidDto));

        Assertions.assertEquals("RFID tag already exist! with same tagId", exception.getMessage()); // Same EPC
    }

    @Test
    public void testUpdateRadioFreqID_sameEPC_throwAlreadyExistException() {

        this.radioFreqIdRepository.save(RFIDTag.builder()
                .id(2L)
                .siteName("Warehouse A")
                .epc("EPC33")
                .tagId("TAG987654321")
                .location("Shelf 111, Aisle 2")
                .rssi("-601dBm")
                .date(LocalDate.now())
                .build());

        RFIDTagDto rfidDto = RFIDTagDto.builder()
                .siteName("Warehouse C")
                .epc("EPC33")
                .build();

        RadioFreqIdAlreadyExistException exception = assertThrows(RadioFreqIdAlreadyExistException.class,
                () -> this.radioFreqIdService.updateRadioFreqID("1", rfidDto));

        Assertions.assertEquals("RFID tag already exist! with same EPC", exception.getMessage()); // Same EPC
    }

    @Test
    public void testUpdateRadioFreqID() {

        RFIDTagDto rfidDto = RFIDTagDto.builder()
                .siteName("Warehouse C")
                .tagId("TAGCCCCCCCCCCC")
                .build();

        RFIDTagDto response = this.radioFreqIdService.updateRadioFreqID("1", rfidDto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(rfidDto.getSiteName(), response.getSiteName());
        Assertions.assertEquals(rfidDto.getTagId(), response.getTagId());
    }

    @Test
    public void testDeleteRadioFreqId_throwRadioFreqIdNotFoundException() {

        RadioFreqIdNotFoundException exception = assertThrows(RadioFreqIdNotFoundException.class,
                () -> this.radioFreqIdService.deleteRadioFreqId("9"));

        Assertions.assertEquals("RFID Tag not found!", exception.getMessage());
    }

    @Test
    public void testDeleteRadioFreqId() {

        this.radioFreqIdService.deleteRadioFreqId("1");

        Optional<RFIDTag> response = this.radioFreqIdRepository.findById(1L);

        Assertions.assertTrue(response.isEmpty());
    }
}
