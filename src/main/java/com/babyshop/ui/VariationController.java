package com.babyshop.ui;

import com.babyshop.backend.Product;
import com.babyshop.backend.Variation;
import com.babyshop.backend.VariationDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class VariationController {

    @FXML
    private Button btnAddVariation;

    @FXML
    private TableColumn<Variation, Integer> colVariationId;

    @FXML
    private TableColumn<Variation, String> colVariationType;

    @FXML
    private TableColumn<Variation, String> colVariationValue;

    @FXML
    private TableColumn<Variation, String> colAction;

    @FXML
    private Label messageLabel;

    @FXML
    private TableView<Variation> variationTable;

    @FXML
    private TextField variationTypeField;

    @FXML
    private TextField variationValueField;

    private ObservableList<Variation> variationList;
    private VariationDAO variationDAO;

    public VariationController() {
        variationDAO = new VariationDAO();
        variationList = FXCollections.observableArrayList();
    }

    @FXML
    void handleAddVariation(ActionEvent event) {
        String variationType = variationTypeField.getText();
        String variationValue = variationValueField.getText();
        variationTypeField.clear();
        variationValueField.clear();

        messageLabel.setText("Variation added successfully!");
    }

    public void initialize() {
        colVariationId.setCellValueFactory(cellData -> cellData.getValue().getVariationIdProperty().asObject());
        colVariationType.setCellValueFactory(cellData -> cellData.getValue().getVariationTypeProperty());
        colVariationValue.setCellValueFactory(cellData -> cellData.getValue().getVariationValueProperty());

        // Setup the actions column with icons (update and delete buttons)
        colAction.setCellValueFactory(param -> null); // No data binding for actions column
        colAction.setCellFactory(param -> new TableCell<Product, String>() {
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
        variationTable.setItems(variationList);
        loadVariationsFromDatabase();  // Load variations from the database at initialization

    }

    private void loadVariationsFromDatabase() {
        try {
            variationList.clear();
            variationList.addAll(variationDAO.getAllVariations());
        } catch (SQLException e) {
            messageLabel.setText("Error loading variations from the database.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleUpdateVariation(Variation variation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/UpdateVariationPopup.fxml"));
            VBox root = loader.load();

            UpdateProductPopupController popupController = loader.getController();
            popupController.setVariationController(this); // Pass the reference to this controller
            popupController.initialize(variation);

            Stage stage = new Stage();
            stage.setTitle("Update Variation");
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
