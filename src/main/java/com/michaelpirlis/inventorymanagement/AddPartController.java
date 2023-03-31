package com.michaelpirlis.inventorymanagement;

import com.michaelpirlis.inventorymanagement.models.InHouse;
import com.michaelpirlis.inventorymanagement.models.Outsourced;
import com.michaelpirlis.inventorymanagement.models.Part;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.michaelpirlis.inventorymanagement.models.Inventory.addPart;
import static com.michaelpirlis.inventorymanagement.models.Inventory.getAllParts;

public class AddPartController extends Application implements Initializable {

    @FXML private RadioButton InHouseRadio;
    @FXML private RadioButton OutsourcedRadio;
    @FXML private Label machineIDLabel;
    @FXML private Label companyNameLabel;
    @FXML private TextField machineIDTextField;
    @FXML private TextField companyNameTextField;
    @FXML private TextField partNameTextField;
    @FXML private TextField partInventoryTextField;
    @FXML private TextField partPriceTextField;
    @FXML private TextField partMinTextField;
    @FXML private TextField partMaxTextField;

    @FXML private TableColumn<Object, Object> partIdColumn;
    @FXML private TableColumn<Object, Object> partNameColumn;
    @FXML private TableColumn<Object, Object> partInventoryColumn;
    @FXML private TableColumn<Object, Object> partPriceColumn;
    @FXML private TableView<Part> allPartTable;

    public ToggleGroup PartType;

    private boolean errorCheck = false;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddPartController.class.getResource("add-part.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void initialize(URL location, ResourceBundle resources) {

        InventoryController.partTableSetup(allPartTable, partIdColumn, partNameColumn, partInventoryColumn, partPriceColumn);
    }

    public void OutsourcedEnabled() {
        machineIDLabel.setVisible(false);
        machineIDTextField.setVisible(false);
        companyNameLabel.setVisible(true);
        companyNameTextField.setVisible(true);
        errorCheck = false;
    }

    public void InHouseEnabled() {
        machineIDLabel.setVisible(true);
        machineIDTextField.setVisible(true);
        companyNameLabel.setVisible(false);
        companyNameTextField.setVisible(false);
        errorCheck = false;
    }

    private void resetSaveForm() {
        partNameTextField.clear();
        partPriceTextField.clear();
        partInventoryTextField.clear();
        partMinTextField.clear();
        partMaxTextField.clear();
        machineIDTextField.clear();
        companyNameTextField.clear();
        InHouseRadio.fire();
        InHouseEnabled();
    }

    private void partErrorHandling() {
        StringBuilder errorMessage = new StringBuilder();

        if (partNameTextField.getText().isEmpty()) {
            errorMessage.append("Name is required.\n");
        }

        if (partInventoryTextField.getText().isEmpty()) {
            errorMessage.append("Inventory is required.\n");
        }

        if (partPriceTextField.getText().isEmpty()) {
            errorMessage.append("Price is required.\n");
        }

        if (partMinTextField.getText().isEmpty()) {
            errorMessage.append("Minimum is required.\n");
        }

        if (partMaxTextField.getText().isEmpty()) {
            errorMessage.append("Maximum is required.\n");
        }

        if (InHouseRadio.isSelected() && machineIDTextField.getText().isEmpty()) {
            errorMessage.append("Machine ID is required.\n");
        }

        if (OutsourcedRadio.isSelected() && companyNameTextField.getText().isEmpty()) {
            errorMessage.append("Company name is required.\n");
        }

        try {
            Integer.parseInt(partInventoryTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage.append("Inventory requires a number.\n");
        }

        try {
            Double.parseDouble(partPriceTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage.append("Price requires a number.\n");
        }

        try {
            Integer.parseInt(partMinTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage.append("Minimum requires a number.\n");
        }

        try {
            Integer.parseInt(partMaxTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage.append("Maximum requires a number.\n");
        }

        if (errorMessage.length() > 0) {
            Alert saveError = new Alert(Alert.AlertType.INFORMATION);
            saveError.setTitle("Unable To Save");
            saveError.setHeaderText(null);
            saveError.setContentText(errorMessage.toString());
            saveError.showAndWait();
        }

        if (errorMessage.length() == 0) {
            errorCheck = true;
        }
    }

    private void logicErrorHandling() {
        StringBuilder errorMessage = new StringBuilder();
        int min = Integer.parseInt(partMinTextField.getText());
        int max = Integer.parseInt(partMaxTextField.getText());
        int inventory = Integer.parseInt(partInventoryTextField.getText());

        if (min > max) {
            errorMessage.append("Minimum cannot be greater than maximum.\n");
        }

        if (inventory < min || inventory > max) {
            errorMessage.append("Inventory must be between minimum and maximum.\n");
        }

        if (errorMessage.length() > 0) {
            Alert saveError = new Alert(Alert.AlertType.INFORMATION);
            saveError.setTitle("Unable To Save");
            saveError.setHeaderText(null);
            saveError.setContentText(errorMessage.toString());
            saveError.showAndWait();

            errorCheck = false;
        }
    }

    @FXML
    private void savePartButton() {
        int partGeneratedId = getAllParts().size() + 1;

        partErrorHandling();

        if (errorCheck) {
            logicErrorHandling();
        }

        if (OutsourcedRadio.isSelected() && errorCheck) {
            Outsourced newPart;
            newPart = new Outsourced(
                    partGeneratedId,
                    partNameTextField.getText(),
                    Double.parseDouble(partPriceTextField.getText()),
                    Integer.parseInt(partInventoryTextField.getText()),
                    Integer.parseInt(partMinTextField.getText()),
                    Integer.parseInt(partMaxTextField.getText()),
                    companyNameTextField.getText()
            );

            addPart(newPart);
            OutsourcedEnabled();
            resetSaveForm();
        }

        if (InHouseRadio.isSelected() && errorCheck){
            InHouse newPart;
            newPart = new InHouse(
                    partGeneratedId,
                    partNameTextField.getText(),
                    Double.parseDouble(partPriceTextField.getText()),
                    Integer.parseInt(partInventoryTextField.getText()),
                    Integer.parseInt(partMinTextField.getText()),
                    Integer.parseInt(partMaxTextField.getText()),
                    Integer.parseInt(machineIDTextField.getText())

            );

            addPart(newPart);
            InHouseEnabled();
            resetSaveForm();
        }
    }

    @FXML
    private void cancelButton(ActionEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("inventory.fxml")));
        Scene mainMenuScene = new Scene(mainMenuParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(mainMenuScene);
        appStage.show();
    }

}
