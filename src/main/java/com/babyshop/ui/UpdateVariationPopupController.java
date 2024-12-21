package com.babyshop.ui;

import com.babyshop.backend.Product;
import com.babyshop.backend.ProductDAO;
import com.babyshop.backend.Variation;
import com.babyshop.backend.VariationDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class UpdateVariationPopupController {

    private VariationController variationController;
    private VariationDAO variationDAO;
    @FXML
    private Button btnUpdate;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField variationIdField;

    @FXML
    private TextField variationTypeField;

    @FXML
    private TextField variationValueField;

    @FXML
    void handleUpdateVariation(ActionEvent event) {
        try {
            int variationId = Integer.parseInt(variationIdField.getText());
            String variationType = variationTypeField.getText();
            String variationValue = variationValueField.getText();

            Variation updatedVariation = new Variation();
            updatedVariation.setVariationId(variationId);
            updatedVariation.setVariationType(variationType);
            updatedVariation.setVariationValue(variationValue);

            variationDAO.updateVariation(updatedVariation);

            messageLabel.setText("Variation updated successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");

            closePopup();

        } catch (SQLException e) {
            messageLabel.setText("Error updating variation.");
            messageLabel.setStyle("-fx-text-fill: red;");
        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter valid data!");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    public void initialize(Variation variation) {
        variationIdField.setText(String.valueOf(variation.getVariationId()));
        variationTypeField.setText(variation.getVariationType());
        variationValueField.setText(String.valueOf(variation.getVariationValue()));
    }

    public UpdateVariationPopupController() {
        variationDAO = new VariationDAO();
    }

    public void setVariationController(VariationController variationController) {
        this.variationController = variationController;
    }
    private void closePopup() {
        if (variationController != null) {
            variationController.refreshVariations();
        }
        Stage stage = (Stage) btnUpdate.getScene().getWindow();
        stage.close();
    }

}
