CREATE TABLE IF NOT EXISTS "order"(
    order_id BIGSERIAL NOT NULL PRIMARY KEY,
    order_number VARCHAR NOT NULL,
    promo_code VARCHAR,
    city VARCHAR,
    street VARCHAR,
    zip_code VARCHAR
);