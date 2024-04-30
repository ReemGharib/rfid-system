package com.rfidtag.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * RFID Tag Entity
 *
 * @author Reem Gharib
 */
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rfid_tag_tbl")
public class RFIDTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "site_name")
    private String siteName; // name of the location where the RFID reader is installed

    @Column(name = "electronic_product_code")
    private String epc; // unique code for identifying individual items

    @Column(name = "tag_id")
    private String tagId; // unique identifier for the RFID tag itself

    private String location; // specific location within the site where the tag was scanned

    @Column(name = "received_signal_strength_ind")
    private String rssi; //measurement of the power present in the received radio signal

    private LocalDate date; // timestamp when the tag was read
}
