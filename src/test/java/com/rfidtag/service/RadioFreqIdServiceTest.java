package com.rfidtag.service;

import com.rfidtag.dtos.RFIDTagDto;
import com.rfidtag.exception.RadioFreqIdNotFoundException;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    public void testGetRadioFreqIDs() {

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
    public void testPersistRadioFreqIDs_throwRadioFreqIdAlreadyExistException() {

        doNothing().when(this.radioFreqIdSupport).validateRFIDTag(rfidTagDto);

        this.radioFreqIdService.persistRadioFreqID(rfidTagDto);
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

        RFIDTag rfidTag = RFIDTag.builder()
                .id(2L)
                .siteName("Warehouse A")
                .epc("EPC123456789")
                .tagId("TAG987654321")
                .build();

        when(this.radioFreqIdRepository.findById(2L)).thenReturn(Optional.empty());
        doThrow(RadioFreqIdNotFoundException.class).when(this.radioFreqIdRepository).delete(rfidTag);

        this.radioFreqIdService.deleteRadioFreqId("1");

        verify(this.radioFreqIdRepository, times(1)).findById(1L);
        verify(this.radioFreqIdRepository, times(1)).delete(any());
    }
}
