CREATE TABLE IF NOT EXISTS item(
    item_id BIGSERIAL NOT NULL PRIMARY KEY,
    product_name VARCHAR NOT NULL,
    quantity INT4 NOT NULL,
    price DECIMAL NOT NULL
);