CREATE TABLE users(
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    verified BOOLEAN  NOT NULL,
    last_seen_on VARCHAR(255) NOT NULL
);

CREATE TABLE sessions(
    id UUID PRIMARY KEY,
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR(48) NOT NULL,
    user_id UUID NOT NULL,
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE messages(
    id UUID PRIMARY KEY,
    from_id UUID NOT NULL,
    to_id UUID NOT NULL,
    _timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    content TEXT NOT NULL,
    delivered BOOLEAN NOT NULL
);

CREATE TABLE call_logs(
    id UUID PRIMARY KEY,
    _timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR(24) NOT NULL,
    duration VARCHAR(48) NOT NULL,
    from_id UUID NOT NULL,
    to_id UUID NOT NULL,
    FOREIGN KEY(from_id) REFERENCES users(id),
    FOREIGN KEY(to_id) REFERENCES users(id)
);