package com.babyshop.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardController {

    @FXML
    private AnchorPane contentArea;

    @FXML
    public void showProducts() {
        loadSection("/ui/products.fxml");
    }

    @FXML
    public void showSales() {
        loadSection("/ui/sales.fxml");
    }

    @FXML
    void showVariations() {
        loadSection("/ui/variations.fxml");
    }

    private void loadSection(String fxmlFile) {
        try {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(FXMLLoader.load(getClass().getResource(fxmlFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDashboard() {
        try {
            Stage stage = new Stage();
            stage.setTitle("Baby Shop POS Dashboard");
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/ui/Dashboard.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
