package com.babyshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the LoginPage.fxml file
        primaryStage.setTitle("Baby Shop POS Login");

        // Load the FXML scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/LoginPage.fxml"));
        Scene scene = new Scene(loader.load());

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Set the width and height of the stage (not the scene)
        primaryStage.setWidth(800);  // Set width to 800px (adjust as needed)
        primaryStage.setHeight(600); // Set height to 600px (adjust as needed)

        // Show the stage
        primaryStage.show();
    }
}
