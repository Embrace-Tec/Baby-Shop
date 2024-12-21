package com.babyshop.ui;

import com.babyshop.backend.Product;
import com.babyshop.backend.ProductDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ProductController {

    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, Integer> colProductId;
    @FXML private TableColumn<Product, String> colName;
    @FXML private TableColumn<Product, Double> colPrice;
    @FXML private TableColumn<Product, Integer> colQuantity;
    @FXML private TableColumn<Product, String> colActions;  // Corrected column declaration for actions
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
        // Setup the columns for displaying product details
        colProductId.setCellValueFactory(cellData -> cellData.getValue().productIdProperty().asObject());
        colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
//        colQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        // Setup the actions column with icons (update and delete buttons)
        colActions.setCellValueFactory(param -> null); // No data binding for actions column
        colActions.setCellFactory(param -> new TableCell<Product, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actionBox = new HBox(10);

                    // Create an update icon
                    ImageView updateIcon = new ImageView(getClass().getResource("/images/update.png").toExternalForm());
                    updateIcon.setFitHeight(20);
                    updateIcon.setFitWidth(20);
                    updateIcon.setOnMouseClicked(event -> handleUpdateProduct(getTableRow().getItem())); // Handle update

                    // Create a delete icon
                    ImageView deleteIcon = new ImageView(getClass().getResource("/images/delete.png").toExternalForm());
                    deleteIcon.setFitHeight(20);
                    deleteIcon.setFitWidth(20);
                    deleteIcon.setOnMouseClicked(event -> handleDeleteProduct(getTableRow().getItem())); // Handle delete

                    actionBox.getChildren().addAll(updateIcon, deleteIcon);
                    setGraphic(actionBox);
                }
            }
        });

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
//            newProduct.setQuantity(quantity);

            // Add the product to the database
            productDAO.addProduct(newProduct);

            // Clear input fields
            productIdField.clear();
            productNameField.clear();
            productPriceField.clear();
            productQuantityField.clear();

            // Show success message
            messageLabel.setText("Product added successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");

            // Reload products from the database to update the TableView
            loadProductsFromDatabase();

        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter valid data!");
            messageLabel.setStyle("-fx-text-fill: red;");
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Error adding product to the database.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleUpdateProduct(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/UpdateProductPopup.fxml"));
            VBox root = loader.load();

            UpdateProductPopupController popupController = loader.getController();
            popupController.setProductController(this); // Pass the reference to this controller
            popupController.initialize(product);

            Stage stage = new Stage();
            stage.setTitle("Update Product");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            messageLabel.setText("Error loading update popup.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }


    public void refreshProducts() {
        loadProductsFromDatabase();
    }

    @FXML
    private void handleDeleteProduct(Product product) {
        System.out.println("Delete product: " + product.getName());
        try {
            productDAO.deleteProduct(product.getProductId());
            messageLabel.setText("Product deleted successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");

            loadProductsFromDatabase();
        } catch (SQLException e) {
            messageLabel.setText("Error deleting product from the database.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }
}
