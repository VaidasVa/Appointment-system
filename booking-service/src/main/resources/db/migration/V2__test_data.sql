use appointment;

INSERT INTO bookings(id, appointment_id, booking_date, cancellation_date, description, user_id)
VALUES ('9667a8ce-6e39-4a87-97f7-5ea5d8ba8030',
        'ed7587da-777d-43e4-92b8-075cf117c1bb',
        '2023-05-09T13:07:11',
        '2023-05-09T13:07:11',
         'Descrip',
          '');
INSERT INTO bookings(id, appointment_id, booking_date, cancellation_date, description, user_id)
VALUES ('9d77803e-5904-4450-91e0-0a19487089e8',
        null,
        '2023-05-19T18:07:10',
        null,
         'Appointment description',
          null);
INSERT INTO bookings(id, appointment_id, booking_date, cancellation_date, description, user_id)
VALUES ('2ecc9502-e204-43f6-a571-27c0e509689c',
        '83787063-0156-4bfb-abde-52258c21495a',
        '2023-05-18T14:33:10',
        '2023-05-18T14:35:55',
        'Cancelled appointment',
        'a29d57e6-00da-4707-ac08-04b9175b0f25');
