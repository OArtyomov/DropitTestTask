CREATE TABLE package
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    tag         varchar(36) NOT NULL,
    delivery_id INT,
    foreign key (delivery_id) references delivery (id)

);