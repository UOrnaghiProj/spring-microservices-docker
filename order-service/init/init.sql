CREATE TABLE IF NOT EXISTS orders (
                                      id SERIAL PRIMARY KEY,
                                      product VARCHAR(100),
    quantity INTEGER,
    user_id INTEGER
    );

INSERT INTO orders (product, quantity, user_id) VALUES
                                                    ('Libro', 2, 1),
                                                    ('Penna', 5, 2);