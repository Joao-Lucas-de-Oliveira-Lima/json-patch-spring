CREATE OR REPLACE FUNCTION populate_order_tags()
    RETURNS void AS
$$
DECLARE
    order_id   INT8;
    num_tags   INT;
    tag_number INT;
BEGIN
    FOR order_id IN SELECT order_id FROM "order"
        LOOP
            num_tags := FLOOR(RANDOM() * 10 + 1);

            FOR tag_number IN 1..num_tags
                LOOP
                    INSERT INTO order_tag (order_tag, tag)
                    VALUES (order_id, 'Tag ' || tag_number);
                END LOOP;
        END LOOP;
END;
$$ LANGUAGE plpgsql;
