CREATE TABLE IF NOT EXISTS order_tag(
    order_tag INT8 NOT NULL,
    tag VARCHAR,
    PRIMARY KEY(order_tag, tag),
    FOREIGN KEY(order_tag) REFERENCES "order"(order_id) ON DELETE CASCADE
);