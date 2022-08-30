USE wedgallery;

create TRIGGER IF NOT EXISTS COPY_ID
    BEFORE INSERT
    ON IMAGE
    FOR EACH ROW
    SET NEW.ORDER_NUMBER = (select AUTO_INCREMENT
                            from information_schema.TABLES
                            where table_schema = database()
                              and TABLE_NAME = 'IMAGE');