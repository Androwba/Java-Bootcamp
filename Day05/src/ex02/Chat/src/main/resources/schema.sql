DROP TABLE IF EXISTS chatroom_members;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS chatroom;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    login VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(30) NOT NULL
);

CREATE TABLE chatroom (
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    owner_id INTEGER REFERENCES users(id)
);

CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    author_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    room_id INTEGER REFERENCES chatroom(id) ON DELETE CASCADE,
    text TEXT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE chatroom_members (
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    chatroom_id INTEGER REFERENCES chatroom(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, chatroom_id)
);
