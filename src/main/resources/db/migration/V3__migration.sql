CREATE TABLE address
(
    id BIGSERIAL PRIMARY KEY,
    specification varchar(255) NOT NULL,
    specification_tsv tsvector NOT NULL
);

alter table address alter column specification_tsv drop not null;

create index on address using gin(specification_tsv);

alter table delivery add  column address_id BIGSERIAL REFERENCES address(id) ON DELETE SET NULL;
alter table delivery alter column address_id drop not null;