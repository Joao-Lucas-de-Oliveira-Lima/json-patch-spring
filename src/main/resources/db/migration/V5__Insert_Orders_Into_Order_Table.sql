DO
$$
    BEGIN
        FOR i IN 1..200
            LOOP
                INSERT INTO "order" (order_number, promo_code, city, street, zip_code)
                VALUES ('ORD' || LPAD(i::text, 5, '0'),
                        'PROMO' || LPAD(i::text, 4, '0'),
                        'City ' || (i % 5 + 1),
                        'Street ' || (i % 10 + 1),
                        'ZIP' || LPAD((i + 1000)::text, 5, '0'));
            END LOOP;
    END
$$;
