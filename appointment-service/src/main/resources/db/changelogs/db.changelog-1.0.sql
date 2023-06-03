--liquibase formatted sql

--changeset salomeja:1
CREATE TABLE buildings (
    id int PRIMARY KEY,
    name varchar(50) NOT NULL,
    city varchar(20) NOT NULL,
    post_code varchar(10) NOT NULL,
    address varchar(50) NOT NULL
);

--changeset salomeja:2
INSERT INTO buildings (id,
                       name,
                       city,
                       post_code,
                       address)
VALUES (101,
        'Office One',
        'Vilnius',
        '120000',
        'Gedimino pr. 13'),
        (102,
        'Office Two',
        'Kaunas',
        '238500',
        'Laisves al. 26A'),
        (103,
        'Office Three',
        'Riga',
        '010010',
        'Alberta lela 36');

----changeset salomeja:3
CREATE TABLE rooms (
    id int PRIMARY KEY,
    name varchar(255) NOT NULL,
    building_id int NOT NULL
);

----changeset salomeja:4
INSERT INTO rooms      (id,
                       name,
                       building_id)
VALUES (101,
        'Procedure Room 1',
        101),
        (102,
        'Exam Room 1',
        101),
        (103,
        'Exam Room 2',
        101),
        (104,
        'Procedure Room 1',
        102),
        (105,
        'Exam Room 1',
        102),
        (106,
        'Exam Room 2',
        102),
        (107,
        'Procedure Room 1',
        103),
        (108,
        'Exam Room 1',
        103),
        (109,
        'Exam Room 2',
        103);

----changeset salomeja:5
CREATE TABLE specialities (
    id int PRIMARY KEY,
    name varchar(255) NOT NULL
);

----changeset salomeja:6
INSERT INTO specialities (id,
                         name)
VALUES (101,
        'Family doctor'),
        (102,
        'Physiotherapist'),
        (103,
        'Dentist'),
        (104,
        'Psychologist');

----changeset salomeja:7
CREATE TABLE specialists (
    id varchar(36) PRIMARY KEY,
    user_id varchar(36) NOT NULL,
    speciality_id int NOT NULL
);

----changeset salomeja:8
INSERT INTO specialists (id,
                         user_id,
                         speciality_id)
VALUES ('8cfea70a-00c3-11ee-be56-0242ac120002',
        '12010a25-cc72-44a4-bf1a-87eb23a5ccff',
        101),
        ('0cd0fbec-62c7-427d-90a8-1ef685ae099d',
        '67701079-d873-4773-a5a3-d3d23885949e',
        101),
        ('a62b9d2c-aa51-4b51-85b7-ee0ed6478621',
        '178516a7-b66e-4a48-b82d-b78937aacefb',
        102),
        ('4c76aad3-7bef-496e-9b4b-6d7a05208079',
        '285b0b7b-5dbc-4da5-b27e-8d5f9381f130',
        103),
        ('4de0e3a6-0045-43b9-8a16-4d49b7df92cc',
        'ff3a2014-b399-41f8-808f-fa2163e14f6b',
        104);

--changeset salomeja:9
CREATE TABLE appointments (
    id varchar(36) PRIMARY KEY,
    start datetime NOT NULL,
    end datetime NOT NULL,
    specialist_id varchar(36) NOT NULL,
    room_id int NOT NULL,
    price double NOT NULL
);

--changeset salomeja:10
INSERT INTO appointments (id,
                          start,
                          end,
                          specialist_id,
                          room_id,
                          price)
VALUES ('39979895-4e1e-48ae-a036-3378d95671e4',
        '2023-02-01 11:00:00',
        '2023-02-01 12:00:00',
        '8cfea70a-00c3-11ee-be56-0242ac120002',
        101,
        85.00),
        ('422fd150-b9a6-41b3-9658-687236c0996d',
        '2023-02-01 09:00:00',
        '2023-02-01 09:30:00',
        '0cd0fbec-62c7-427d-90a8-1ef685ae099d',
        102,
        65.00),
        ('06668e80-fca2-498d-9160-deba24fd6ff0',
        '2023-02-01 09:00:00',
        '2023-02-01 09:30:00',
        'a62b9d2c-aa51-4b51-85b7-ee0ed6478621',
        103,
        65.00),
        ('6c603514-297c-48d7-b42f-4de55481f123',
        '2023-02-02 09:00:00',
        '2023-02-02 09:30:00',
        '4c76aad3-7bef-496e-9b4b-6d7a05208079',
        104,
        65.00),
        ('475133b3-0d6d-4ebb-8b68-e53cac8e0ae3',
        '2023-02-03 16:00:00',
        '2023-02-03 16:45:00',
        '4de0e3a6-0045-43b9-8a16-4d49b7df92cc',
        105,
        70.00);
