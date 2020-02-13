SET SCHEMA public;

CREATE TABLE package
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    tag         varchar(36) NOT NULL,
    delivery_id BIGINT,
    foreign key (delivery_id) references delivery (id)

);