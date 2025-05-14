CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(100),
                                     email VARCHAR(100)
);

INSERT INTO users (name, email) VALUES
                                    ('Mario Rossi', 'mario.rossi@example.com'),
                                    ('Luisa Bianchi', 'luisa.bianchi@example.com');