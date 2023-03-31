package com.michaelpirlis.inventorymanagement;

import com.michaelpirlis.inventorymanagement.models.Inventory;
import com.michaelpirlis.inventorymanagement.models.Part;
import com.michaelpirlis.inventorymanagement.models.Product;
import javafx.application.Application;
import javafx.application.Platform;
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

import static com.michaelpirlis.inventorymanagement.models.Inventory.lookupPart;
import static com.michaelpirlis.inventorymanagement.models.Inventory.lookupProduct;


public class InventoryController extends Application implements Initializable {

    @FXML private TableColumn<Object, Object> partIdColumn;
    @FXML private TableColumn<Object, Object> partNameColumn;
    @FXML private TableColumn<Object, Object> partInventoryColumn;
    @FXML private TableColumn<Object, Object> partPriceColumn;
    @FXML private TableColumn<Object, Object> productIdColumn;
    @FXML private TableColumn<Object, Object> productNameColumn;
    @FXML private TableColumn<Object, Object> productInventoryColumn;
    @FXML private TableColumn<Object, Object> productPriceColumn;
    @FXML private TableView<Product> allProductTable;
    @FXML private TableView<Part> allPartTable;
    @FXML private TextField partSearch;
    @FXML private TextField productSearch;
    public static Part modifyPart;
    public static Product modifyProduct;


    public void initialize(URL location, ResourceBundle resources) {

        partTableSetup(allPartTable, partIdColumn, partNameColumn, partInventoryColumn, partPriceColumn);
        productTableSetup(allProductTable, productIdColumn, productNameColumn, productInventoryColumn, productPriceColumn);
    }

    static void partTableSetup(TableView<Part> allPartTable,
                               TableColumn<Object, Object> partIdColumn,
                               TableColumn<Object, Object> partNameColumn,
                               TableColumn<Object, Object> partInventoryColumn,
                               TableColumn<Object, Object> partPriceColumn) {
        allPartTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    static void productTableSetup(TableView<Product> allProductTable,
                                  TableColumn<Object, Object> productIdColumn,
                                  TableColumn<Object, Object> productNameColumn,
                                  TableColumn<Object, Object> productInventoryColumn,
                                  TableColumn<Object, Object> productPriceColumn) {
        allProductTable.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryController.class.getResource("inventory.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void searchAlert() {
        Alert searchError = new Alert(Alert.AlertType.INFORMATION);
        searchError.setTitle("No Search Results Found");
        searchError.setHeaderText(null);
        searchError.setContentText("Search was unable to locate a valid ID or Name" +
                "\nPlease verify your search criteria and try your search again.");
        searchError.showAndWait();
    }

    private void noSelection() {
        Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
        noSelection.setTitle("No Selection");
        noSelection.setHeaderText(null);
        noSelection.setContentText("Please select an item from the table to modify.");
        noSelection.showAndWait();
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
    private void searchProducts(){
        String searchString = productSearch.getText();
        ObservableList<Product> searchList = lookupProduct(searchString);
        allProductTable.setItems(searchList);

        if (searchList.isEmpty()) {
            int searchInteger;

            try {
                searchInteger = Integer.parseInt(searchString);
                searchList = lookupProduct(searchInteger);
                allProductTable.setItems(searchList);
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }

            if (searchList.isEmpty()) {
                searchAlert();
                allProductTable.setItems(Inventory.getAllProducts());
            }
        }

        productSearch.clear();
    }

    @FXML
    private void addProductButton(ActionEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add-product.fxml")));
        Scene mainMenuScene = new Scene(mainMenuParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(mainMenuScene);
        appStage.show();
    }

    @FXML
    private void modifyProductButton(ActionEvent event) throws IOException {
        modifyProduct = allProductTable.getSelectionModel().getSelectedItem();

        if (modifyProduct == null) {
            noSelection();
        } else {
                Parent mainMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("modify-product.fxml")));
                Scene mainMenuScene = new Scene(mainMenuParent);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(mainMenuScene);
                appStage.show();
            }
        }

    @FXML
    private void addPartButton(ActionEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add-part.fxml")));
        Scene mainMenuScene = new Scene(mainMenuParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(mainMenuScene);
        appStage.show();
    }

    @FXML
    private void modifyPartButton(ActionEvent event) throws IOException {
        modifyPart = allPartTable.getSelectionModel().getSelectedItem();

        if (modifyPart == null) {
            noSelection();
        } else {
            Parent mainMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("modify-part.fxml")));
            Scene mainMenuScene = new Scene(mainMenuParent);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(mainMenuScene);
            appStage.show();
        }
    }

    @FXML
    private void deletePartButton() {
        Part selectedPart = allPartTable.getSelectionModel().getSelectedItem();

        Alert confirmPartDeletion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmPartDeletion.setTitle("Confirm Part Deletion");
        confirmPartDeletion.setHeaderText(null);
        confirmPartDeletion.setContentText("Would you like to delete the part " + (selectedPart.getName()) +" ?" +
                "\nThis action is final and cannot be undone.");

        Optional<ButtonType> result = confirmPartDeletion.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
        }
    }

    @FXML
    private void deleteProductButton() {
        Product selectedProduct = allProductTable.getSelectionModel().getSelectedItem();

        Alert confirmProductDeletion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmProductDeletion.setTitle("Confirm Product Deletion");
        confirmProductDeletion.setHeaderText(null);
        confirmProductDeletion.setContentText("Would you like to delete the product " + (selectedProduct.getName()) +" ?" +
                "\nThis action is final and cannot be undone.");

        Optional<ButtonType> result = confirmProductDeletion.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deleteProduct(selectedProduct);
        }
    }

    @FXML
    private void exitButtonClicked() {
        Platform.exit();
    }

    public static void main(String[] args) {
        launch();
    }

}