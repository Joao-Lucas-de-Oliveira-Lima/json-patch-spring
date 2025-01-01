CREATE TABLE IF NOT EXISTS order_tags(
    order_order_id INT8 NOT NULL,
    tags VARCHAR,
    FOREIGN KEY(order_order_id) REFERENCES "order"(order_id) ON DELETE CASCADE
);