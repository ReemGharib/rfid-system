package com.rfidtag.service.impl;

import com.rfidtag.dtos.RFIDTagDto;
import com.rfidtag.model.RFIDTag;
import com.rfidtag.repository.RadioFreqIDRepository;
import com.rfidtag.service.RadioFreqIDService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Radio Frequency Identification service implements RadioFreqIDService
 */
@Transactional
@Service
public class RadioFreqIDServiceImpl implements RadioFreqIDService {

    @Autowired
    private RadioFreqIDRepository radioFreqIDRepository;

    @Override
    public List<RFIDTagDto> getRadioFreqIDs(String epc, String tagId, String location, String siteName) {

        List<RFIDTag> rfidTagDtoList = this.radioFreqIDRepository.findAllByEpcOrTagIdOrSiteNameOrLocation(epc, tagId, siteName, location);

        return rfidTagDtoList.stream()
                .map(rfidTagDto ->
                        RFIDTagDto.builder()
                                .id(rfidTagDto.getId().toString())
                                .siteName(rfidTagDto.getSiteName())
                                .epc(rfidTagDto.getEpc())
                                .tagId(rfidTagDto.getTagId())
                                .location(rfidTagDto.getLocation())
                                .rssi(rfidTagDto.getRssi())
                                .date(rfidTagDto.getDate())
                                .build())
                .collect(Collectors.toList());
    }

    @Override
    public RFIDTagDto persistRadioFreqID(RFIDTagDto rfidTagDto) {

        RFIDTag.builder()
                .id(rfidTagDto.getId())
                .siteName()
                .build();
        this.radioFreqIDRepository.save()
        return null;
    }

    // correct request

}
