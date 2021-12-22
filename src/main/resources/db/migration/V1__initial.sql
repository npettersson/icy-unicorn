CREATE TABLE bus_line
(
    id          INTEGER      NOT NULL,
    designation VARCHAR(100) NOT NULL,
    exists_from DATE         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE bus_stop
(
    id           INTEGER      NOT NULL,
    stop_area_id INTEGER      NOT NULL,
    name         VARCHAR(100) NOT NULL,
    north_coord  VARCHAR(20)  NOT NULL,
    east_coord   VARCHAR(20)  NOT NULL,
    zone         CHAR(1)      NOT NULL,
    exists_from  DATE         NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX idx_bus_stop_name ON bus_stop (name);

CREATE TABLE bus_stop_on_line
(
    bus_line_id INTEGER NOT NULL,
    bus_stop_id INTEGER NOT NULL,
    direction   CHAR(1) NOT NULL,
    stop_order  INTEGER NOT NULL,
    exists_from DATE    NOT NULL,
    PRIMARY KEY (bus_line_id, bus_stop_id, direction),
    FOREIGN KEY (bus_line_id) REFERENCES bus_line (id),
    FOREIGN KEY (bus_stop_id) REFERENCES bus_stop (id)
);
