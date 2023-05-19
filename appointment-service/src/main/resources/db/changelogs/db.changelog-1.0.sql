--liquibase formatted sql

--changeset salomeja:1
DROP TABLE IF EXISTS appointments;

--changeset salomeja:2
CREATE TABLE appointments (
    id varchar(36) PRIMARY KEY,
    start datetime NOT NULL,
    end datetime NOT NULL,
    specialist_id varchar(36) NOT NULL,
    room_id int NOT NULL,
    price double NOT NULL
);

--changeset salomeja:3
INSERT INTO appointments (id,
                          start,
                          end,
                          specialist_id,
                          room_id,
                          price)
VALUES ('d6326e2f-067f-4d61-b6d0-14e9278172f8',
        '2023-02-01 09:00:00',
        '2023-02-01 09:30:00',
        '167ccdb2-f594-11ed-b67e-0242ac120002',
        125,
        65.00);

--changeset salomeja:4
INSERT INTO appointments (id,
                          start,
                          end,
                          specialist_id,
                          room_id,
                          price)
VALUES ('4cc717a6-61bb-4e57-8169-137245f95c15',
        '2023-02-01 11:00:00',
        '2023-02-01 12:00:00',
        '3f06d610-45f6-4d51-86ac-6da5bf2331c4',
        2,
        85.00),
        ('c85e0321-2144-423c-a8d4-20836af70472',
        '2023-02-01 09:00:00',
        '2023-02-01 09:30:00',
        '6ef10c42-29ac-4b9a-9c4e-47cf27e18941',
        3,
        65.00),
        ('1223c4bd-db11-41e1-bcaa-7dc14dcbfdb0',
        '2023-02-01 09:00:00',
        '2023-02-01 09:30:00',
        'd7f3cf90-612c-4a92-9416-360b5796b2d7',
        2,
        65.00),
        ('9c463185-ffde-435f-a957-bc15c492e808',
        '2023-02-02 09:00:00',
        '2023-02-02 09:30:00',
        '0feaf25d-1c55-465c-947b-547a2197dfcd',
        1,
        65.00),
        ('efa24e13-1d3a-4d9a-b74b-15881c7ff7df',
        '2023-02-03 16:00:00',
        '2023-02-03 16:45:00',
        '1436721a-1868-47dd-b177-344c4ad7b8f0',
        2,
        70.00);