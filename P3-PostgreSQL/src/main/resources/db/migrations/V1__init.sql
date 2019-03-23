CREATE TABLE IF NOT EXISTS task (
    id UUID PRIMARY KEY,
    text VARCHAR NOT NULL,
    status VARCHAR NOT NULL,
    createdat timestamp with time zone NOT NULL
);
