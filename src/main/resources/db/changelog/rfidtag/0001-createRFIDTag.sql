CREATE TABLE IF NOT EXISTS rfid_tag_tbl (
id BIGINT                       AUTO_INCREMENT PRIMARY KEY NOT NULL,
site_name                       VARCHAR(255),
electronic_product_code         VARCHAR(255) NOT NULL,
tag_id                          VARCHAR(255) NOT NULL,
location                        VARCHAR(255),
received_signal_strength_ind    VARCHAR(255),
date                            DATE
);