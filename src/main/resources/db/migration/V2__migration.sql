CREATE TABLE delivery
(
    id BIGSERIAL PRIMARY KEY,
    name        varchar(36) NOT NULL,
    address_id BIGSERIAL REFERENCES address(id) ON DELETE SET NULL
);

alter table delivery alter column address_id drop not null;