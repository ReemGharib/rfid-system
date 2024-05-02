package com.rfidtag.service;

import com.rfidtag.dtos.RFIDTagDto;
import com.rfidtag.exception.RadioFreqIdAlreadyExistException;
import com.rfidtag.exception.RadioFreqIdDeleteException;
import com.rfidtag.exception.RadioFreqIdNotFoundException;
import com.rfidtag.exception.RadioFreqIdPersistException;
import com.rfidtag.model.RFIDTag;
import com.rfidtag.repository.RadioFreqIdRepository;
import com.rfidtag.service.impl.RadioFreqIdServiceImpl;
import com.rfidtag.support.RadioFreqIdSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author Reem Ghari
 */
@ExtendWith(MockitoExtension.class)
public class RadioFreqIdServiceTest {

    @InjectMocks
    private RadioFreqIdServiceImpl radioFreqIdService;

    @Mock
    private RadioFreqIdRepository radioFreqIdRepository;

    @Mock
    private RadioFreqIdSupport radioFreqIdSupport;

    private RFIDTagDto rfidTagDto;
    private RFIDTag rfidTag;

    @BeforeEach
    public void setUp() {

        rfidTagDto = RFIDTagDto.builder()
                .id("1")
                .siteName("Warehouse A")
                .epc("EPC123456789")
                .tagId("TAG987654321")
                .location("Shelf 1, Aisle 2")
                .rssi("-60dBm")
                .build();

        rfidTag = RFIDTag.builder()
                .id(1L)
                .siteName("Warehouse A")
                .epc("EPC123456789")
                .tagId("TAG987654321")
                .location("Shelf 1, Aisle 2")
                .rssi("-60dBm")
                .build();
    }

    @Test
    public void testGetRadioFreqIds() {

        when(this.radioFreqIdRepository.filterTags(rfidTagDto)).thenReturn(Specification.allOf());
        when(this.radioFreqIdRepository.findAll(Specification.anyOf())).thenReturn(Arrays.asList(rfidTag));
        when(this.radioFreqIdSupport.populateRFIDTagDto(rfidTag)).thenReturn(rfidTagDto);

        List<RFIDTagDto> response = this.radioFreqIdService.getRadioFreqIDs(rfidTagDto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());

        verify(this.radioFreqIdRepository, times(1)).filterTags(rfidTagDto);
        verify(this.radioFreqIdRepository, times(1)).findAll(Specification.anyOf());
        verify(this.radioFreqIdSupport, times(1)).populateRFIDTagDto(rfidTag);
    }

    @Test
    public void testPersistRadioFreqId() {

        RFIDTagDto rf = RFIDTagDto.builder()
                .siteName("Warehouse A")
                .epc("EPC123456789")
                .tagId("TAG987654321")
                .location("Shelf 1, Aisle 2")
                .rssi("-60dBm")
                .date(LocalDate.now())
                .build();

        RFIDTag rfidTag = RFIDTag.builder()
                .siteName("Warehouse A")
                .epc("EPC123456789")
                .tagId("TAG987654321")
                .location("Shelf 1, Aisle 2")
                .rssi("-60dBm")
                .date(LocalDate.now())
                .build();

        doNothing().when(this.radioFreqIdSupport).validateRFIDTag(rf);
        doNothing().when(this.radioFreqIdRepository)
                .checkRFIDExistsByTagIdOrEpc(rf.getTagId(), rf.getEpc());

        when(this.radioFreqIdRepository.save(rfidTag)).thenReturn(rfidTag);
        when(radioFreqIdSupport.populateRFIDTagDto(rfidTag)).thenReturn(RFIDTagDto.builder()
                .id("1")
                .siteName("Warehouse A")
                .epc("EPC123456789")
                .tagId("TAG987654321")
                .location("Shelf 1, Aisle 2")
                .rssi("-60dBm")
                .date(LocalDate.now())
                .build());

        RFIDTagDto response = radioFreqIdService.persistRadioFreqID(rf);

        Assertions.assertNotNull(response);

        verify(this.radioFreqIdSupport, times(1)).validateRFIDTag(rf);
        verify(this.radioFreqIdRepository, times(1))
                .checkRFIDExistsByTagIdOrEpc(rf.getTagId(), rf.getEpc());
        verify(this.radioFreqIdRepository, times(1)).save(rfidTag);
        verify(radioFreqIdSupport, times(1)).populateRFIDTagDto(rfidTag);
    }

    @Test
    public void testPersistRadioFreqId_throwRadioFreqIdPersistException() {

        doNothing().when(this.radioFreqIdSupport).validateRFIDTag(rfidTagDto);
        doNothing().when(this.radioFreqIdRepository)
                .checkRFIDExistsByTagIdOrEpc(rfidTagDto.getTagId(), rfidTagDto.getEpc());

        when(this.radioFreqIdRepository.save(rfidTag)).thenThrow(RadioFreqIdPersistException.class);

        RadioFreqIdPersistException exception = assertThrows(RadioFreqIdPersistException.class,
                () -> this.radioFreqIdService.persistRadioFreqID(rfidTagDto));

        assertEquals("Unable to persist RFID tag of id [1]", exception.getMessage());

        verify(this.radioFreqIdSupport, times(1)).validateRFIDTag(rfidTagDto);
        verify(this.radioFreqIdRepository, times(1))
                .checkRFIDExistsByTagIdOrEpc(rfidTagDto.getTagId(), rfidTagDto.getEpc());
        verify(this.radioFreqIdRepository, times(1)).save(any());
        verify(this.radioFreqIdSupport, times(0)).populateRFIDTagDto(any());
    }


    @Test
    public void testUpdateRadioFreqId() {

        RFIDTagDto rfidRequest = RFIDTagDto.builder()
                .siteName("Warehouse B")
                .location("Shelf 3, Aisle 2")
                .build();

        RFIDTag rfidTag = RFIDTag.builder()
                .id(1L)
                .siteName("Warehouse A")
                .epc("EPC123456789")
                .tagId("TAG987654321")
                .location("Shelf 1, Aisle 2")
                .rssi("-60dBm")
                .date(LocalDate.now())
                .build();

        RFIDTagDto updatedRFID = RFIDTagDto.builder()
                .id("1")
                .siteName("Warehouse B")
                .epc("EPC123456789")
                .tagId("TAG987654321")
                .location("Shelf 3, Aisle 2")
                .rssi("-60dBm")
                .date(LocalDate.now())
                .build();

        when(this.radioFreqIdRepository.findById(1L)).thenReturn(Optional.of(rfidTag));
        when(this.radioFreqIdRepository.save(rfidTag)).thenReturn(rfidTag);
        when(this.radioFreqIdSupport.populateRFIDTagDto(rfidTag)).thenReturn(updatedRFID);

        RFIDTagDto response = this.radioFreqIdService.updateRadioFreqID("1", rfidRequest);

        Assertions.assertNotNull(response);
        assertEquals("1", response.getId());

        verify(radioFreqIdRepository, times(1)).findById(1L);
        verify(radioFreqIdRepository, times(0)).findByEpcEqualsAndIdIsNot(anyString(), anyLong());
        verify(radioFreqIdRepository, times(0)).findByTagIdEqualsAndIdIsNot(anyString(), anyLong());
        verify(radioFreqIdRepository, times(1)).save(rfidTag);
    }

    @Test
    public void testUpdateRadioFreqId_throwRadioFreqIdPersistException() {

        when(this.radioFreqIdRepository.findById(1L)).thenReturn(Optional.of(rfidTag));
        when(radioFreqIdRepository.findByEpcEqualsAndIdIsNot(rfidTagDto.getEpc(), 1L)).thenReturn(Optional.empty());
        when(radioFreqIdRepository.findByTagIdEqualsAndIdIsNot(rfidTagDto.getTagId(), 1L)).thenReturn(Optional.empty());
        when(this.radioFreqIdRepository.save(rfidTag)).thenThrow(RadioFreqIdPersistException.class);

        RadioFreqIdPersistException exception = assertThrows(RadioFreqIdPersistException.class,
                () -> this.radioFreqIdService.updateRadioFreqID("1", rfidTagDto));

        assertEquals("Unable to update RFID tag of id [1]", exception.getMessage());

        verify(radioFreqIdRepository, times(1)).findById(1L);
        verify(radioFreqIdRepository, times(1)).findByEpcEqualsAndIdIsNot(rfidTagDto.getEpc(), 1L);
        verify(radioFreqIdRepository, times(1)).findByTagIdEqualsAndIdIsNot(rfidTagDto.getTagId(), 1L);
        verify(radioFreqIdRepository, times(1)).save(rfidTag);
    }

    @Test
    public void testUpdateRadioFreqId_sameEPC_throwRadioFreqIdAlreadyExistException() {

        RFIDTagDto rfidRequest = RFIDTagDto.builder()
                .siteName("Warehouse B")
                .epc("EPC123456789")
                .build();

        RFIDTag rfidTag = RFIDTag.builder()
                .id(1L)
                .siteName("Warehouse A")
                .epc("EPC123456789")
                .tagId("TAG987654321")
                .location("Shelf 1, Aisle 2")
                .rssi("-60dBm")
                .date(LocalDate.now())
                .build();

        when(this.radioFreqIdRepository.findById(1L)).thenReturn(Optional.of(rfidTag));
        when(this.radioFreqIdRepository.findByEpcEqualsAndIdIsNot("EPC123456789", 1L))
                .thenReturn(Optional.of(RFIDTag.builder().build()));

        RadioFreqIdAlreadyExistException exception = assertThrows(RadioFreqIdAlreadyExistException.class,
                () -> this.radioFreqIdService.updateRadioFreqID("1", rfidRequest));

        Assertions.assertNotNull(exception);
        assertEquals("RFID tag already exist! with same EPC", exception.getMessage());

        verify(radioFreqIdRepository, times(1)).findById(1L);
        verify(radioFreqIdRepository, times(1)).findByEpcEqualsAndIdIsNot("EPC123456789", 1L);
        verify(radioFreqIdRepository, times(0)).save(any());
        verify(radioFreqIdSupport, times(0)).populateRFIDTagDto(any());
    }

    @Test
    public void testUpdateRadioFreqId_sameTagId_throwRadioFreqIdAlreadyExistException() {

        String tagId = "TAG987654321";

        RFIDTagDto rfidRequest = RFIDTagDto.builder()
                .siteName("Warehouse B")
                .tagId(tagId)
                .build();

        RFIDTag rfidTag = RFIDTag.builder()
                .id(1L)
                .siteName("Warehouse A")
                .epc("EPC123456789")
                .tagId(tagId)
                .location("Shelf 1, Aisle 2")
                .rssi("-60dBm")
                .date(LocalDate.now())
                .build();

        when(this.radioFreqIdRepository.findById(1L)).thenReturn(Optional.of(rfidTag));
        when(this.radioFreqIdRepository.findByTagIdEqualsAndIdIsNot(tagId, 1L))
                .thenReturn(Optional.of(RFIDTag.builder().build()));

        RadioFreqIdAlreadyExistException exception = assertThrows(RadioFreqIdAlreadyExistException.class,
                () -> this.radioFreqIdService.updateRadioFreqID("1", rfidRequest));

        Assertions.assertNotNull(exception);
        assertEquals("RFID tag already exist! with same tagId", exception.getMessage());

        verify(radioFreqIdRepository, times(1)).findById(1L);
        verify(radioFreqIdRepository, times(0)).findByEpcEqualsAndIdIsNot(rfidRequest.getEpc(), 1L);
        verify(radioFreqIdRepository, times(1)).findByTagIdEqualsAndIdIsNot(tagId, 1L);
        verify(radioFreqIdRepository, times(0)).save(any());
        verify(radioFreqIdSupport, times(0)).populateRFIDTagDto(any());
    }


    @Test
    public void testDeleteRadioFreqId() {


        when(this.radioFreqIdRepository.findById(1L)).thenReturn(Optional.of(rfidTag));
        doNothing().when(this.radioFreqIdRepository).delete(rfidTag);

        this.radioFreqIdService.deleteRadioFreqId("1");

        verify(this.radioFreqIdRepository, times(1)).findById(1L);
        verify(this.radioFreqIdRepository, times(1)).delete(rfidTag);
    }

    @Test
    public void testDeleteRadioFreqId_throwRadioFreqIdNotFoundException() {

        when(this.radioFreqIdRepository.findById(2L)).thenReturn(Optional.empty());

        RadioFreqIdNotFoundException exception = assertThrows(RadioFreqIdNotFoundException.class,
                () -> this.radioFreqIdService.deleteRadioFreqId("2"));

        Assertions.assertEquals("RFID Tag not found!", exception.getMessage());

        verify(this.radioFreqIdRepository, times(1)).findById(2L);
        verify(this.radioFreqIdRepository, times(0)).delete(any());
    }

    @Test
    public void testDeleteRadioFreqId_throwRadioFreqIdDeleteException() {

        RFIDTag rfidTag = RFIDTag.builder()
                .id(2L)
                .siteName("Warehouse A")
                .epc("EPC123456789")
                .tagId("TAG987654321")
                .build();

        when(this.radioFreqIdRepository.findById(2L)).thenReturn(Optional.of(rfidTag));
        doThrow(RadioFreqIdDeleteException.class).when(this.radioFreqIdRepository).delete(rfidTag);

        RadioFreqIdDeleteException exception = assertThrows(RadioFreqIdDeleteException.class,
                () -> this.radioFreqIdService.deleteRadioFreqId("2"));

        Assertions.assertEquals("Unable to delete RFID tag of id [{2}]", exception.getMessage());

        verify(this.radioFreqIdRepository, times(1)).findById(2L);
        verify(this.radioFreqIdRepository, times(1)).delete(rfidTag);
    }
}
