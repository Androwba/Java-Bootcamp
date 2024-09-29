INSERT INTO users (login, password) VALUES
('john', '1234'),
('alice', '0000'),
('bob', '7777'),
('william', '123456789'),
('sophia', 'my_password');

INSERT INTO chatroom (name, owner_id) VALUES
('General', 2),
('Random', 1),
('Memes', 5),
('Movies', 1),
('Games', 4);

INSERT INTO messages (author_id, room_id, text) VALUES
(1, 1, 'Hello World!'),
(2, 1, 'Hi my name is Alice'),
(3, 3, 'I like that meme'),
(4, 4, 'Let''s play some games'),
(5, 5, 'Any good movie recommendations?');

INSERT INTO chatroom_members (user_id, chatroom_id) VALUES
(1, 1),
(2, 1),
(3, 3),
(4, 4),
(5, 5),
(2, 2);
