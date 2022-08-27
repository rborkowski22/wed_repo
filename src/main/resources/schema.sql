USE wedgallery;
DROP TABLE IF EXISTS IMAGE;
CREATE TABLE IMAGE (
                       ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                       NAME VARCHAR(255) NOT NULL,
                       PHOTO MEDIUMBLOB DEFAULT NULL,
                       ORDER_NUMBER BIGINT
);