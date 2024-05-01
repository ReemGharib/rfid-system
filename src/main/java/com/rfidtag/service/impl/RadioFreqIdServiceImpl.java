package com.rfidtag.service.impl;

import com.rfidtag.dtos.RFIDTagDto;
import com.rfidtag.exception.RadioFreqIdDeleteException;
import com.rfidtag.exception.RadioFreqIdNotFoundException;
import com.rfidtag.exception.RadioFreqIdPersistException;
import com.rfidtag.model.RFIDTag;
import com.rfidtag.repository.RadioFreqIdRepository;
import com.rfidtag.service.RadioFreqIdService;
import com.rfidtag.support.RadioFreqIdSupport;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Radio Frequency Identification service implements RadioFreqIdService
 *
 * @author Reem Gharib
 */
@Log4j2
@Transactional
@RequiredArgsConstructor
@Service
public class RadioFreqIdServiceImpl implements RadioFreqIdService {

    private final RadioFreqIdRepository radioFreqIDRepository;
    private final RadioFreqIdSupport radioFreqIdSupport;

    @Override
    public List<RFIDTagDto> getRadioFreqIDs(RFIDTagDto rfidTagDto) {

        log.info("Retrieving radio freq IDs for {}", rfidTagDto);

        List<RFIDTag> rfidTagDtoList = this.radioFreqIDRepository
                .findAll(this.radioFreqIDRepository.filterTags(rfidTagDto));

        log.info("Retrieved {} radio freq IDs", rfidTagDtoList.size());

        return rfidTagDtoList.stream()
                .map(this.radioFreqIdSupport::populateRFIDTagDto)
                .collect(Collectors.toList());
    }

    @Override
    public RFIDTagDto persistRadioFreqID(RFIDTagDto rfidTagDto) {

        log.info("Persisting radio freq ID {}", rfidTagDto.getId());

        this.radioFreqIdSupport.validateRFIDTag(rfidTagDto);

        // Check if RFIDTag already exists
        this.radioFreqIDRepository.checkRFIDExistsByTagIdOrEpc(rfidTagDto.getTagId(), rfidTagDto.getEpc());

        RFIDTag rfid = RFIDTag.builder()
                .siteName(rfidTagDto.getSiteName())
                .epc(rfidTagDto.getEpc())
                .tagId(rfidTagDto.getTagId())
                .location(rfidTagDto.getLocation())
                .rssi(rfidTagDto.getRssi())
                .date(LocalDate.now())
                .build();

        try {
            this.radioFreqIDRepository.save(rfid);
        } catch (Exception e) {

            log.error("Unable to save RFID tag {} ", rfidTagDto.getId());
            throw new RadioFreqIdPersistException(format("Unable to persist RFID tag of id [%s]", rfidTagDto.getId()));
        }

        log.info("RFID tag {} persisted successfully!", rfidTagDto.getId());
        return this.radioFreqIdSupport.populateRFIDTagDto(rfid);
    }

    @Override
    public RFIDTagDto updateRadioFreqID(String id, RFIDTagDto rfidTagDto) {

        log.info("Updating RFID tag : [{}]", id);

        RFIDTag rfidTag = this.radioFreqIDRepository.findById(Long.valueOf(id)).orElseThrow(() ->
                new RadioFreqIdNotFoundException("RFID tag not found!"));

        // Check if RFIDTag already exists
        this.radioFreqIDRepository.checkRFIDExistsByTagIdOrEpc(rfidTagDto.getTagId(), rfidTagDto.getEpc());

        rfidTag.setSiteName(rfidTagDto.getSiteName());
        rfidTag.setEpc(rfidTagDto.getEpc());
        rfidTag.setTagId(rfidTagDto.getTagId());
        rfidTag.setLocation(rfidTagDto.getLocation());
        rfidTag.setRssi(rfidTagDto.getRssi());
        rfidTag.setDate(rfidTagDto.getDate());

        try {
            this.radioFreqIDRepository.save(rfidTag);
        } catch (Exception e) {

            log.error("Unable to update RFID tag of id [{}] -- ERROR :{}", id, e.getMessage());
            throw new RadioFreqIdPersistException(format("Unable to update RFID tag of id [%s]", id));
        }

        log.info("RFID tag successfully updated !");
        return rfidTagDto;
    }

    @Override
    public void deleteRadioFreqId(String id) {

        log.info("Attempt to delete Radio Freq Identification: [{}]", id);

        RFIDTag rfidTag = this.radioFreqIDRepository.findById(Long.valueOf(id)).orElseThrow(() ->
                new RadioFreqIdNotFoundException("RFID Tag not found!"));

        try {
            this.radioFreqIDRepository.delete(rfidTag);
        } catch (Exception e) {

            log.error("Unable to delete RFID tag of id [{}]", id);
            throw new RadioFreqIdDeleteException(format("Unable to delete RFID tag of id [{%s}]", id));
        }

        log.info("Radio Freq Identification: {} successfully deleted", id);
    }
}
