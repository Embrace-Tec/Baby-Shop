-- Create the baby_shop database if it doesn't exist
CREATE DATABASE IF NOT EXISTS baby_shop;

-- Use the baby_shop database
USE baby_shop;

-- Create the products table
CREATE TABLE IF NOT EXISTS products (
                                        product_id INT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
                                        price DECIMAL(10, 2) NOT NULL,
                                        quantity INT NOT NULL
);

-- Create the sales table
CREATE TABLE IF NOT EXISTS sales (
                                     sale_id INT AUTO_INCREMENT PRIMARY KEY,
                                     product_id INT NOT NULL,
                                     quantity_sold INT NOT NULL,
                                     sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Optional: Insert sample data
INSERT INTO products (name, price, quantity) VALUES
                                                 ('Baby Stroller', 150.00, 20),
                                                 ('Baby Bottle', 20.00, 50),
                                                 ('Baby Crib', 300.00, 10);

INSERT INTO sales (product_id, quantity_sold) VALUES
                                                  (1, 2),
                                                  (2, 5);
