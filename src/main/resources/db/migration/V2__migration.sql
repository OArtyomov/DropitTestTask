CREATE TABLE package
(
    id BIGSERIAL PRIMARY KEY,
    tag varchar(36) NOT NULL,
    delivery_id BIGSERIAL REFERENCES delivery(id) ON DELETE CASCADE
);

alter table package alter column delivery_id drop not null;