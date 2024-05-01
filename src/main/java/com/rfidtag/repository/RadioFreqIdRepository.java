package com.rfidtag.repository;

import com.rfidtag.dtos.RFIDTagDto;
import com.rfidtag.exception.RadioFreqIdAlreadyExistException;
import com.rfidtag.model.RFIDTag;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Radio frequency identification repository
 *
 * @author Reem Gharib
 */
@Repository
public interface RadioFreqIdRepository extends JpaRepository<RFIDTag, Long> {

    List<RFIDTag> findAll(Specification<RFIDTag> specification);

    /**
     * Find RFID by id
     *
     * @param id the id
     * @return Optional class of RFIDTag
     */
    Optional<RFIDTag> findById(Long id);

    /**
     * Find RFID by tag id and epc
     *
     * @param tagId the tag id
     * @return Optional class of RFIDTag
     */
    List<RFIDTag> findByTagIdOrEpc(String tagId, String epc);

    /**
     * Check if RFID Tag already exists by tagId
     *
     * @param tagId the tagId of RFID Tag
     */
    default void checkRFIDExistsByTagIdOrEpc(String tagId, String epc) {

        if (!CollectionUtils.isEmpty(findByTagIdOrEpc(tagId, epc))) {
            throw new RadioFreqIdAlreadyExistException(String.format("RFID Tag already exists",
                    tagId, epc));
        }
    }

    /**
     * Filter RFIDTags
     *
     * @param rfidTagDto the rfid tag dto
     * @return Specification
     */
    default Specification<RFIDTag> filterTags(RFIDTagDto rfidTagDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (rfidTagDto.getSiteName() != null) {
                predicates.add(criteriaBuilder.equal(root.get("siteName"), rfidTagDto.getSiteName()));
            }

            if (rfidTagDto.getEpc() != null) {
                predicates.add(criteriaBuilder.equal(root.get("epc"), rfidTagDto.getEpc()));
            }

            if (rfidTagDto.getTagId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("tagId"), rfidTagDto.getTagId()));
            }

            if (rfidTagDto.getLocation() != null) {
                predicates.add(criteriaBuilder.equal(root.get("location"), rfidTagDto.getLocation()));
            }

            if (rfidTagDto.getRssi() != null) {
                predicates.add(criteriaBuilder.equal(root.get("rssi"), rfidTagDto.getRssi()));
            }

            if (rfidTagDto.getDate() != null) {
                predicates.add(criteriaBuilder.equal(root.get("date"), rfidTagDto.getDate()));
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
