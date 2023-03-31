package com.michaelpirlis.inventorymanagement;

import com.michaelpirlis.inventorymanagement.models.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.michaelpirlis.inventorymanagement.InventoryController.modifyProduct;
import static com.michaelpirlis.inventorymanagement.models.Inventory.lookupPart;

public class ModifyProductController extends Application implements Initializable {

    @FXML private TableColumn<Object, Object> partIdColumn;
    @FXML private TableColumn<Object, Object> partNameColumn;
    @FXML private TableColumn<Object, Object> partInventoryColumn;
    @FXML private TableColumn<Object, Object> partPriceColumn;
    @FXML private TableView<Part> allPartTable;

    @FXML private TableColumn<Part, Integer> associatedIdColumn;
    @FXML private TableColumn<Part, String> associatedNameColumn;
    @FXML private TableColumn<Part, Integer> associatedInventoryColumn;
    @FXML private TableColumn<Part, Double> associatedPriceColumn;
    @FXML private TableView<Part> associatedTable;

    @FXML private TextField partSearch;
    @FXML private TextField productID;
    @FXML private TextField productName;
    @FXML private TextField productInventory;
    @FXML private TextField productPrice;
    @FXML private TextField productMinimum;
    @FXML private TextField productMaximum;

    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private boolean errorCheck = false;
    private int index;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ModifyProductController.class.getResource("modify-product.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void initialize(URL location, ResourceBundle resources) {

        modifyProductImport();
        InventoryController.partTableSetup(allPartTable, partIdColumn, partNameColumn, partInventoryColumn, partPriceColumn);
        associatedPartTable();
    }

    private void associatedPartTable() {
        associatedIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedTable.setItems(associatedParts);
    }

    private void modifyProductImport() {
        if (modifyProduct != null) {
            try {
                productID.setText(String.valueOf(modifyProduct.getId()));
                productName.setText(modifyProduct.getName());
                productInventory.setText(String.valueOf(modifyProduct.getStock()));
                productPrice.setText(String.valueOf(modifyProduct.getPrice()));
                productMinimum.setText(String.valueOf(modifyProduct.getMin()));
                productMaximum.setText(String.valueOf(modifyProduct.getMax()));
                associatedParts.addAll(modifyProduct.getAllAssociatedParts());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            index = modifyProduct.getId() - 1;
        }
    }

    private void searchAlert() {
        Alert searchError = new Alert(Alert.AlertType.INFORMATION);
        searchError.setTitle("No Search Results Found");
        searchError.setHeaderText(null);
        searchError.setContentText("Search was unable to locate a valid ID or Name" +
                "\nPlease verify your search criteria and try your search again.");
        searchError.showAndWait();
    }

    @FXML
    private void searchParts() {
        String searchString = partSearch.getText();
        ObservableList<Part> searchList = lookupPart(searchString);
        allPartTable.setItems(searchList);

        if (searchList.isEmpty()) {
            int searchInteger;

            try {
                searchInteger = Integer.parseInt(searchString);
                searchList = lookupPart(searchInteger);
                allPartTable.setItems(searchList);
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }

            if (searchList.isEmpty()) {
                searchAlert();
                allPartTable.setItems(Inventory.getAllParts());
            }
        }

        partSearch.clear();
    }

    @FXML
    private void addAssociatedButton() {
        Part selectedPart = allPartTable.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            associatedParts.add(selectedPart);
            associatedTable.setItems(associatedParts);
            allPartTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void removeAssociatedButton() {
        Part selectedPart = associatedTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            Alert confirmPartDeletion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmPartDeletion.setTitle("Confirm Associated Part Removal");
            confirmPartDeletion.setHeaderText(null);
            confirmPartDeletion.setContentText("Would you like to remove the associated part " + (selectedPart.getName()) + " ?");

            Optional<ButtonType> result = confirmPartDeletion.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                associatedTable.setItems(associatedParts);
                associatedTable.getSelectionModel().clearSelection();
            }
        }
    }

    private void productErrorHandling() {
        StringBuilder errorMessage = new StringBuilder();

        if (productName.getText().isEmpty()) {
            errorMessage.append("Name is required.\n");
        }

        if (productInventory.getText().isEmpty()) {
            errorMessage.append("Inventory is required.\n");
        }

        if (productPrice.getText().isEmpty()) {
            errorMessage.append("Price is required.\n");
        }

        if (productMinimum.getText().isEmpty()) {
            errorMessage.append("Minimum is required.\n");
        }

        if (productMaximum.getText().isEmpty()) {
            errorMessage.append("Maximum is required.\n");
        }

        try {
            Integer.parseInt(productInventory.getText());
        } catch (NumberFormatException e) {
            errorMessage.append("Inventory requires a number.\n");
        }

        try {
            Double.parseDouble(productPrice.getText());
        } catch (NumberFormatException e) {
            errorMessage.append("Price requires a number.\n");
        }

        try {
            Integer.parseInt(productMinimum.getText());
        } catch (NumberFormatException e) {
            errorMessage.append("Minimum requires a number.\n");
        }

        try {
            Integer.parseInt(productMaximum.getText());
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
        int min = Integer.parseInt(productMinimum.getText());
        int max = Integer.parseInt(productMaximum.getText());
        int inventory = Integer.parseInt(productInventory.getText());

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
    private void saveProductButton(ActionEvent event) throws IOException {

        productErrorHandling();

        if (errorCheck) {
            logicErrorHandling();
        }

        if (errorCheck) {
            Product newProduct = new Product(
                    Integer.parseInt(productID.getText()),
                    productName.getText(),
                    Double.parseDouble(productPrice.getText()),
                    Integer.parseInt(productInventory.getText()),
                    Integer.parseInt(productMinimum.getText()),
                    Integer.parseInt(productMaximum.getText()));

            for (Part part : associatedParts) {
                newProduct.addAssociatedPart(part);
            }

            Inventory.updateProduct(index, newProduct);
            errorCheck = false;
            returnHome(event);
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

    private void returnHome(ActionEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("inventory.fxml")));
        Scene mainMenuScene = new Scene(mainMenuParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(mainMenuScene);
        appStage.show();
    }
}