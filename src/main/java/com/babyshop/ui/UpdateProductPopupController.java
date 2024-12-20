package com.babyshop.ui;

import com.babyshop.backend.Product;
import com.babyshop.backend.ProductDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
/**
 * @author : L.H.J
 * @File: UpdateProductPopupController
 * @mailto : lharshana2002@gmail.com
 * @created : 2024-12-20, Friday
 **/
public class UpdateProductPopupController {

    private ProductController productController;

    @FXML private TextField productIdField;
    @FXML private TextField productNameField;
    @FXML private TextField productPriceField;
    @FXML private TextField productQuantityField;
    @FXML private Label messageLabel;
    @FXML private Button btnUpdate;

    private ProductDAO productDAO;

    public UpdateProductPopupController() {
        productDAO = new ProductDAO();
    }



    public void initialize(Product product) {
        productIdField.setText(String.valueOf(product.getProductId()));
        productNameField.setText(product.getName());
        productPriceField.setText(String.valueOf(product.getPrice()));
        productQuantityField.setText(String.valueOf(product.getQuantity()));
    }

    @FXML
    private void handleUpdateProduct() {
        try {
            int productId = Integer.parseInt(productIdField.getText());
            String name = productNameField.getText();
            double price = Double.parseDouble(productPriceField.getText());
            int quantity = Integer.parseInt(productQuantityField.getText());

            Product updatedProduct = new Product();
            updatedProduct.setProductId(productId);
            updatedProduct.setName(name);
            updatedProduct.setPrice(price);
            updatedProduct.setQuantity(quantity);

            productDAO.updateProduct(updatedProduct);

            messageLabel.setText("Product updated successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");

            closePopup();

        } catch (SQLException e) {
            messageLabel.setText("Error updating product.");
            messageLabel.setStyle("-fx-text-fill: red;");
        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter valid data!");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void closePopup() {
        if (productController != null) {
            productController.refreshProducts();
        }
        Stage stage = (Stage) btnUpdate.getScene().getWindow();
        stage.close();
    }


    public void setProductController(ProductController productController) {
        this.productController = productController;
    }
}
