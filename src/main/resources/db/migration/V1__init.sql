CREATE TABLE users (
    id UUID PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    is_work BOOLEAN NOT NULL
);

CREATE TABLE addresses (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE dishes (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    composition TEXT NOT NULL,
    ccal INTEGER NOT NULL,
    weight INTEGER NOT NULL,
    price INTEGER NOT NULL,
    kitchen_type VARCHAR(50) NOT NULL,
    dish_type VARCHAR(50) NOT NULL
);

CREATE TABLE dish_images (
    id UUID PRIMARY KEY,
    image_url TEXT NOT NULL,
    dish_id UUID NOT NULL REFERENCES dishes(id) ON DELETE CASCADE
);

CREATE TABLE orders (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    address TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,
    user_id UUID NOT NULL REFERENCES users(id),
    courier_id UUID REFERENCES users(id)
);

CREATE TABLE order_dishes (
    order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    dish_id UUID NOT NULL REFERENCES dishes(id),
    quantity INTEGER NOT NULL,
    PRIMARY KEY (order_id, dish_id)
);

