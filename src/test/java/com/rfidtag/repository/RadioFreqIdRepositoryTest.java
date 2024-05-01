package com.rfidtag.repository;

import com.rfidtag.model.RFIDTag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Reem Gharib
 */
@ActiveProfiles({"test"})
@DataJpaTest
public class RadioFreqIdRepositoryTest {

    @Autowired
    private RadioFreqIdRepository radioFreqIdRepository;

    // Entities and DTOs
    private RFIDTag rfidTag;

    @BeforeEach
    public void setUp() {

        rfidTag = RFIDTag.builder()
                .id(1L)
                .siteName("Warehouse A")
                .epc("EPC123456789")
                .tagId("TAG987654321")
                .location("Shelf 1, Aisle 2")
                .rssi("-60dBm")
                .date(LocalDate.now())
                .build();

    }

    @Test
    public void testSave() {

        this.radioFreqIdRepository.save(this.rfidTag);

        RFIDTag result = this.radioFreqIdRepository.findById(rfidTag.getId()).get();
        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindById() {

        this.radioFreqIdRepository.save(this.rfidTag);

        RFIDTag rfidTag2 = RFIDTag.builder()
                .id(1L)
                .siteName("Warehouse B")
                .epc("EPC12345")
                .tagId("TAG98765")
                .location("Shelf 1, Aisle 2")
                .rssi("-60dBm")
                .date(LocalDate.now())
                .build();

        this.radioFreqIdRepository.save(rfidTag2);

        RFIDTag result = this.radioFreqIdRepository.findById(this.rfidTag.getId()).get();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(this.rfidTag.getId(), result.getId());

        RFIDTag result2 = this.radioFreqIdRepository.findById(rfidTag2.getId()).get();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(rfidTag2.getId(), result2.getId());
    }

    @Test
    public void testFindByTagIdOrEpc() {

        this.radioFreqIdRepository.save(this.rfidTag);

        String tagId = "TAG987654321"; // found
        String epc = "EPC123456789"; // found

        List<RFIDTag> response = this.radioFreqIdRepository.findByTagIdOrEpc(tagId, epc);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());

        String tagId2 = "KKKK"; // Not found
        String epc2 = "EPC123456789"; // found

        List<RFIDTag> response2 = this.radioFreqIdRepository.findByTagIdOrEpc(tagId2, epc2);
        Assertions.assertNotNull(response2);
        Assertions.assertEquals(1, response2.size());

        String tagId3 = "TAG987654321"; //  found
        String epc3 = "ffff"; // Not found

        List<RFIDTag> response3 = this.radioFreqIdRepository.findByTagIdOrEpc(tagId3, epc3);
        Assertions.assertNotNull(response3);
        Assertions.assertEquals(1, response3.size());

        String tagId4 = "zzz"; // Not found
        String epc4 = "ffff"; // Not found

        List<RFIDTag> response4 = this.radioFreqIdRepository.findByTagIdOrEpc(tagId4, epc4);
        Assertions.assertTrue(response4.isEmpty());
        Assertions.assertEquals(0, response4.size());
    }
}
