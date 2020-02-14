CREATE TABLE package
(
    id BIGSERIAL PRIMARY KEY,
    tag varchar(36) NOT NULL,
    delivery_id BIGSERIAL,
    FOREIGN KEY (delivery_id)
        REFERENCES delivery(id)
        ON DELETE CASCADE
);