DO
$$
    BEGIN
        FOR i IN 1..400
            LOOP
                INSERT INTO item (product_name, quantity, price)
                VALUES (CONCAT('Product ', i),
                        (floor(random() * 20) + 1)::INT,
                        (floor(random() * 10000) / 100)::DECIMAL);
            END LOOP;
    END;
$$;
