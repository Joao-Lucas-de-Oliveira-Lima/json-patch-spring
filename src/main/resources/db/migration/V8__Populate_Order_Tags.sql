DO $$
    DECLARE
        v_order_id INT8;
        num_tags INT;
        i INT;
    BEGIN
        FOR v_order_id IN (SELECT o.order_id FROM "order" o) LOOP
                num_tags := trunc(random() * 10 + 1);

                FOR i IN 1..num_tags LOOP
                        INSERT INTO order_tags (order_order_id, tags)
                        VALUES (v_order_id, 'Tag ' || i || ' - ' || md5(random()::text));
                    END LOOP;
            END LOOP;
    END $$;
