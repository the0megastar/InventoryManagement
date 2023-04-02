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


/** The Add Part Controller class launches GUI and form that allows Part creation. */
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


    /** Loads the add part FXML file for the application, sets up the scene, and displays the stage.*/
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddPartController.class.getResource("add-part.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /** Initializes the part table. I expanded the scope to have the part table on the Add Part screen. This allows
     * for multiple parts to be created and the results to be seen instantly by the user to increase user
     * feedback and rapid part creation. */
    public void initialize(URL location, ResourceBundle resources) {

        InventoryController.partTableSetup(allPartTable, partIdColumn, partNameColumn, partInventoryColumn, partPriceColumn);
    }

    /** Used to set the labels and text fields for Outsourced and to hide the InHouse label and text field. */
    public void OutsourcedEnabled() {
        machineIDLabel.setVisible(false);
        machineIDTextField.setVisible(false);
        companyNameLabel.setVisible(true);
        companyNameTextField.setVisible(true);
        errorCheck = false;
    }

    /** Used to set the labels and text fields for InHouse and to hide the Outsourced label and text field. */
    public void InHouseEnabled() {
        machineIDLabel.setVisible(true);
        machineIDTextField.setVisible(true);
        companyNameLabel.setVisible(false);
        companyNameTextField.setVisible(false);
        errorCheck = false;
    }

    /** Used to clear all text fields and enable the InHouse radio button and form. Created a default view. */
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

    /** Used to check all text fields verify they are not empty. Includes checking for values to catch
     * number exceptions. I made the section exceptionally verbose to show a specific message on which field
     * is impacted and if a number is entered into the correct fields. I could have easily created a generic
     * message that states all fields need t be completed and that an empty field was detected. With the current
     * implementation a future enhancement could be to use a css style sheet and highlight the fields with a color
     * such as red. I also added an error check to prevent empty fields from progressing further and causing additional
     * null exceptions. This logic is reused multiple times and kept private in each instance. */
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

    /** After validating the fields have content, the logic checks are implemented to make sure all values are within
     * scope. Future checks can easily be incorporated into this section. If this section fails, the error check will
     * be false to prevent further exceptions and progressions to the save. This logic is also reused multiple
     * times and kept private. */
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

    /** Saves the part as InHouse or Outsourced. Runs Error handling and logic checks prior to the save.
     * each section requires the error check to be true to continue. Event after a part is saved value is set
     * to false. I noticed you could save a part and the error check would remain true allowing a bypass back to
     * the save which created an exception. I also clear all fields and reset the forms fo further integrity. */
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

    /** Loads the inventory FXML file to return back to the main screen when the user presses "Cancel".*/
    @FXML
    private void cancelButton(ActionEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("inventory.fxml")));
        Scene mainMenuScene = new Scene(mainMenuParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(mainMenuScene);
        appStage.show();
    }

}
