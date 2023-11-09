CREATE TABLE IF NOT EXISTS wallet (
    id VARCHAR(255) NOT NULL,
    customer_id VARCHAR(255),
    paper_id VARCHAR(255),
    amount INTEGER,
    PRIMARY KEY (id)
);