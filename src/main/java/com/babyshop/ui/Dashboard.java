package com.babyshop.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class Dashboard {

    public void showDashboard() {
        try {
            Stage stage = new Stage();
            BorderPane root = new BorderPane();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Dashboard.fxml"));

            root.setCenter(loader.load());
            Scene scene = new Scene(root, 1200, 800);
            stage.setTitle("Baby Shop POS Dashboard");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
