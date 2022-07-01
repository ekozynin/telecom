create table customer (
    id INT PRIMARY KEY,
    first_name varchar(100) not null,
    last_name varchar(100) not null
);

create table phone_number (
    id INT PRIMARY KEY,
    customer_id INT,
    number varchar(100) not null
);

INSERT INTO customer (id, first_name, last_name) VALUES (1, 'Michael', 'Wazowsky');

INSERT INTO phone_number (id, customer_id, number) VALUES (1, 1, '0400000001');
INSERT INTO phone_number (id, customer_id, number) VALUES (2, 1, '0400000002');