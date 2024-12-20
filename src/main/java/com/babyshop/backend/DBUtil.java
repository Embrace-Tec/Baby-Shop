package com.babyshop.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author : L.H.J
 * @File: DBUtil
 * @mailto : lharshana2002@gmail.com
 * @created : 2024-12-19, Thursday
 **/
public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3307/baby_shop";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to create the database if it does not exist
//    public static void createDatabaseIfNotExists() {
//        try (Connection connection = getConnection();
//             Statement stmt = connection.createStatement()) {
//
//            // Create the baby_shop database if it does not exist
//            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS baby_shop";
//            stmt.executeUpdate(createDatabaseSQL);
//
//            // Use the baby_shop database
//            String useDatabaseSQL = "USE baby_shop";
//            stmt.executeUpdate(useDatabaseSQL);
//
//            // Create the products table if it does not exist
//            String createProductsTableSQL = """
//                    CREATE TABLE IF NOT EXISTS products (
//                        product_id INT AUTO_INCREMENT PRIMARY KEY,
//                        name VARCHAR(255) NOT NULL,
//                        price DECIMAL(10, 2) NOT NULL,
//                        quantity INT NOT NULL
//                    )""";
//            stmt.executeUpdate(createProductsTableSQL);
//
//            // Create the sales table if it does not exist
//            String createSalesTableSQL = """
//                    CREATE TABLE IF NOT EXISTS sales (
//                        sale_id INT AUTO_INCREMENT PRIMARY KEY,
//                        product_id INT NOT NULL,
//                        quantity_sold INT NOT NULL,
//                        sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//                        FOREIGN KEY (product_id) REFERENCES products(product_id)
//                    )""";
//            stmt.executeUpdate(createSalesTableSQL);
//
//            System.out.println("Database and tables created successfully.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
