CREATE TABLE Customer (
    customerID TEXT PRIMARY KEY,
    name       TEXT NOT NULL,
    email      TEXT NOT NULL UNIQUE,
    phone      TEXT
);

CREATE TABLE Flight (
    flightNumber   TEXT PRIMARY KEY,
    origin         TEXT NOT NULL,
    destination    TEXT NOT NULL,
    departureTime  TEXT NOT NULL,
    arrivalTime    TEXT NOT NULL,
    capacity       INTEGER NOT NULL,
    seatsReserved  INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE Payment (
    paymentID TEXT PRIMARY KEY,
    amount    REAL NOT NULL,
    method    TEXT NOT NULL,
    status    TEXT NOT NULL
);


CREATE TABLE Reservation (
    reservationID TEXT PRIMARY KEY,
    customerID    TEXT NOT NULL,
    flightNumber  TEXT NOT NULL,
    status        TEXT NOT NULL,
    seatNumber    TEXT,
    paymentID     TEXT,

    FOREIGN KEY (customerID)
        REFERENCES Customer(customerID)
        ON UPDATE CASCADE ON DELETE CASCADE,

    FOREIGN KEY (flightNumber)
        REFERENCES Flight(flightNumber)
        ON UPDATE CASCADE ON DELETE CASCADE,

    FOREIGN KEY (paymentID)
        REFERENCES Payment(paymentID)
        ON UPDATE CASCADE ON DELETE SET NULL
);
