use appointment;

DROP TABLE IF EXISTS bookings;

CREATE TABLE bookings (
    id                  VARCHAR(36) NOT NULL PRIMARY KEY,
    appointment_id      VARCHAR(36),
    booking_date        DATETIME(6) NOT NULL,
    cancellation_date   DATETIME(6),
    description         VARCHAR(255) NOT NULL,
    user_id             VARCHAR(36)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

