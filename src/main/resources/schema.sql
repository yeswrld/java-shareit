DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS items CASCADE;
DROP TABLE IF EXISTS booking CASCADE;
DROP TABLE IF EXISTS comments CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    user_id   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    email     VARCHAR(512) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS items
(
    item_id   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    item_name VARCHAR(255) NOT NULL,
    item_description VARCHAR(255) NOT NULL,
    item_available BOOLEAN NOT NULL,
    owner_id    INT REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS booking (
    booking_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    booking_start TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    booking_end TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    item_id INTEGER NOT NULL,
    booker_id INTEGER NOT NULL,
    status VARCHAR(64) NOT NULL,
    CONSTRAINT fk_item_id FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE,
    CONSTRAINT fk_booker_id FOREIGN KEY (booker_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comments (
    comment_id SERIAL PRIMARY KEY,
    text VARCHAR(1000) NOT NULL,
    author_id INT NOT NULL,
    item_id INT NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_author
        FOREIGN KEY (author_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_item
        FOREIGN KEY (item_id)
        REFERENCES items(item_id)
        ON DELETE CASCADE
);
