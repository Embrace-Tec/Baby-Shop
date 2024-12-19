package com.babyshop.ui;

import com.babyshop.backend.Product;
import com.babyshop.backend.ProductDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.sql.SQLException;

public class ProductController {

    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, Integer> colProductId;
    @FXML private TableColumn<Product, String> colName;
    @FXML private TableColumn<Product, Double> colPrice;
    @FXML private TableColumn<Product, Integer> colQuantity;
    @FXML private TextField productIdField;
    @FXML private TextField productNameField;
    @FXML private TextField productPriceField;
    @FXML private TextField productQuantityField;
    @FXML private Label messageLabel;

    private ObservableList<Product> productList;
    private ProductDAO productDAO;

    public ProductController() {
        productList = FXCollections.observableArrayList();
        productDAO = new ProductDAO();  // Initialize the ProductDAO
    }

    @FXML
    public void initialize() {
        colProductId.setCellValueFactory(cellData -> cellData.getValue().productIdProperty().asObject());
        colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        colQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        productsTable.setItems(productList);
        loadProductsFromDatabase();  // Load products from the database at initialization
    }

    private void loadProductsFromDatabase() {
        try {
            productList.clear();
            productList.addAll(productDAO.getAllProducts());
        } catch (SQLException e) {
            messageLabel.setText("Error loading products from the database.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    public void handleAddProduct(ActionEvent event) {
        System.out.println("Add Product button clicked!");
        try {
            // Parse inputs
            int productId = Integer.parseInt(productIdField.getText());
            String name = productNameField.getText();
            double price = Double.parseDouble(productPriceField.getText());
            int quantity = Integer.parseInt(productQuantityField.getText());

            // Create a new Product object
            Product newProduct = new Product();
            newProduct.setProductId(productId);
            newProduct.setName(name);
            newProduct.setPrice(price);
            newProduct.setQuantity(quantity);

            // Add the product to the database
            productDAO.addProduct(newProduct);

            // Clear input fields
            productIdField.clear();
            productNameField.clear();
            productPriceField.clear();
            productQuantityField.clear();

            // Show success message
            messageLabel.setText("Product added successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");  // Display success in green color

            // Reload products from the database to update the TableView
            loadProductsFromDatabase();

        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter valid data!");
            messageLabel.setStyle("-fx-text-fill: red;");
        } catch (SQLException e) {
            e.printStackTrace();  // This will help log the specific exception details.
            messageLabel.setText("Error adding product to the database.");
            messageLabel.setStyle("-fx-text-fill: red;");  // Show the error message in red color
        }
    }

}
