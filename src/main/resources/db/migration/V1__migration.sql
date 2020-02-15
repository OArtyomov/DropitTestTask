CREATE TABLE address
(
    id BIGSERIAL PRIMARY KEY,
    specification varchar(255) NOT NULL,
    specification_tsv tsvector
);

create index on address using gin(specification_tsv);