package com.babyshop.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    public void handleLogin() {
        // Dummy login logic (replace with actual authentication logic)
        if (usernameField.getText().equals("admin") && passwordField.getText().equals("password")) {
            messageLabel.setText("");
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.close();  // Close the login window
            new Dashboard().showDashboard();  // Open the dashboard
        } else {
            messageLabel.setText("Invalid credentials!");
        }
    }
}
