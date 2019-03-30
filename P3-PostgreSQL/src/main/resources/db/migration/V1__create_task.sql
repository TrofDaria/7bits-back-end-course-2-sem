CREATE TABLE IF NOT EXISTS task (
    id UUID PRIMARY KEY,
    text VARCHAR NOT NULL,
    status VARCHAR NOT NULL,
    created_at timestamp with time zone NOT NULL
);
