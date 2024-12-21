package com.babyshop.ui;

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

        Variation variation=new Variation();
        variation.setVariationType(variationType);
        variation.setVariationValue(variationValue);

        try {
            boolean added = variationDAO.addVariation(variation);
            if (added){
                messageLabel.setText("Variation added successfully!");
                messageLabel.setStyle("-fx-text-fill: green;");
                loadVariationsFromDatabase();
            }else{
                messageLabel.setText("Variation adding failed!");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (SQLException e) {
            messageLabel.setText("Variation adding failed!");
            messageLabel.setStyle("-fx-text-fill: red;");
            throw new RuntimeException(e);
        }

    }

    public void initialize() {
        colVariationId.setCellValueFactory(cellData -> cellData.getValue().getVariationIdProperty().asObject());
        colVariationType.setCellValueFactory(cellData -> cellData.getValue().getVariationTypeProperty());
        colVariationValue.setCellValueFactory(cellData -> cellData.getValue().getVariationValueProperty());

        // Setup the actions column with icons (update and delete buttons)
        colAction.setCellValueFactory(param -> null); // No data binding for actions column
        colAction.setCellFactory(param -> new TableCell<Variation, String>() {
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
                    updateIcon.setOnMouseClicked(event -> handleUpdateVariation(getTableRow().getItem())); // Handle update

                    // Create a delete icon
                    ImageView deleteIcon = new ImageView(getClass().getResource("/images/delete.png").toExternalForm());
                    deleteIcon.setFitHeight(20);
                    deleteIcon.setFitWidth(20);
                    deleteIcon.setOnMouseClicked(event -> handleDeleteVariation(getTableRow().getItem())); // Handle delete

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

            UpdateVariationPopupController popupController = loader.getController();
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


    public void refreshVariations() {
        loadVariationsFromDatabase();
    }

    @FXML
    private void handleDeleteVariation(Variation variation) {
        System.out.println("Delete variation: " + variation.getVariationType()+" "+variation.getVariationValue());
        try {
            variationDAO.deleteVariation(variation.getVariationId());
            messageLabel.setText("Variation deleted successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");

            loadVariationsFromDatabase();
        } catch (SQLException e) {
            messageLabel.setText("Error deleting variation from the database.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

}
