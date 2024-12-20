package com.babyshop.ui;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

public class SalesController {

    @FXML
    private TextField productField;
    @FXML
    private TextField amountField;
    @FXML
    private DatePicker saleDatePicker;
    @FXML
    private TableView<?> salesTable;
    @FXML
    private TableColumn<?, ?> colSaleId;
    @FXML
    private TableColumn<?, ?> colProduct;
    @FXML
    private TableColumn<?, ?> colAmount;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private Label messageLabel;

    @FXML
    public void handleAddSale() {
        String product = productField.getText();
        String amount = amountField.getText();
        String saleDate = saleDatePicker.getValue().toString();
        productField.clear();
        amountField.clear();
        saleDatePicker.setValue(null);

        messageLabel.setText("Sale added successfully!");
    }
}
