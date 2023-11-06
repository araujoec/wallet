CREATE TABLE IF NOT EXISTS paper (
    id SERIAL NOT NULL,
    document VARCHAR(11),
    amount DECIMAL,
    PRIMARY KEY (id)
);