package com.rfidtag.repository;

import com.rfidtag.model.RFIDTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Radio frequency identification repository
 */
@Repository
public interface RadioFreqIDRepository extends JpaRepository<RFIDTag, Long> {

    List<RFIDTag> findAllByEpcOrTagIdOrSiteNameOrLocation(String epc,
                                                          String tagId,
                                                          String siteName,
                                                          String location);

    @Query("SELECT r.id, r.siteName, r.epc, r.tagId, r.location, r.rssi, r.date FROM RFIDTag r WHERE r.epc = :epc OR r.tagId = :tagId OR r.location = :location")
    List<RFIDTag> findAllRFIDTags(String epc,
                                  String tagId,
                                  String location);




}
