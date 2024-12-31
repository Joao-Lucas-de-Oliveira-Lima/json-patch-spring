DO
$$
    DECLARE
        order_count       INT;
        item_count        INT;
        random_item_id    INT;
        random_item_count INT;
    BEGIN

        SELECT COUNT(*) INTO order_count FROM "order";

        SELECT COUNT(*) INTO item_count FROM item;

        FOR order_id IN 1..order_count
            LOOP
                random_item_count := (floor(random() * 10) + 1)::INT;

                FOR i IN 1..random_item_count
                    LOOP
                        random_item_id := (floor(random() * item_count) + 1)::INT;

                        INSERT INTO order_item (order_id, item_id)
                        VALUES (order_id, random_item_id);
                    END LOOP;
            END LOOP;
    END;
$$;
