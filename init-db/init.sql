-- Create the baby_shop database if it doesn't exist
CREATE DATABASE IF NOT EXISTS baby_shop;

-- Use the baby_shop database
USE baby_shop;

-- Create the products table
CREATE TABLE IF NOT EXISTS products (
                                        product_id INT PRIMARY KEY AUTO_INCREMENT,
                                        name VARCHAR(255),
                                        price DECIMAL(10, 2)
);

CREATE TABLE IF NOT EXISTS variations (
                            variation_id INT PRIMARY KEY AUTO_INCREMENT,
                            attribute_name VARCHAR(100), -- e.g., Size, Color
                            attribute_value VARCHAR(100) -- e.g., Large, Red
);

CREATE TABLE IF NOT EXISTS product_variation_stock (
                                   product_variation_stock_id INT PRIMARY KEY AUTO_INCREMENT,
                                   product_id INT,
                                   variation_id INT,
                                   stock_level INT,
                                   FOREIGN KEY (product_id) REFERENCES products(product_id),
                                   FOREIGN KEY (variation_id) REFERENCES variations(variation_id)
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
INSERT INTO products (name, price) VALUES
                                                 ('Baby Stroller', 150.00),
                                                 ('Baby Bottle', 20.00),
                                                 ('Baby Crib', 300.00);

INSERT INTO sales (product_id, quantity_sold) VALUES
                                                  (1, 2),
                                                  (2, 5);
