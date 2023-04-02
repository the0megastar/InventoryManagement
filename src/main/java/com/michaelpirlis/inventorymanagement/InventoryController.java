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


/** The Inventory Controller class launches the main GUI of the Inventory Management system.
 * This contains the main tables and buttons for navigation, redirection, addition, modification,
 deletion, and exiting the application. */
public class InventoryController extends Application implements Initializable {

    @FXML private TableColumn<Object, Object> partIdColumn;
    @FXML private TableColumn<Object, Object> partNameColumn;
    @FXML private TableColumn<Object, Object> partInventoryColumn;
    @FXML private TableColumn<Object, Object> partPriceColumn;
    @FXML private TableView<Part> allPartTable;

    @FXML private TableColumn<Object, Object> productIdColumn;
    @FXML private TableColumn<Object, Object> productNameColumn;
    @FXML private TableColumn<Object, Object> productInventoryColumn;
    @FXML private TableColumn<Object, Object> productPriceColumn;
    @FXML private TableView<Product> allProductTable;

    @FXML private TextField partSearch;
    @FXML private TextField productSearch;
    public static Part modifyPart;
    public static Product modifyProduct;

    /** This method initializes the part and product data and tables. */
    public void initialize(URL location, ResourceBundle resources) {

        partTableSetup(allPartTable, partIdColumn, partNameColumn, partInventoryColumn, partPriceColumn);
        productTableSetup(allProductTable, productIdColumn, productNameColumn, productInventoryColumn, productPriceColumn);
    }

    /** Method to configure the columns within the Part Table and assign values then called in the initializer.
     * This method took an exceedingly long time to generate at Mark Kinkead made a correction
     * in the video stating that the getters must be used. After hours, I realized the method worked
     * in the video without the corrected steps. */
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

    /** Method to configure the columns within the Product Table and assign values then called in the initializer.
     * After the issue with the partTableSetup, this was easy as it allowed duplicate code with changing to products
     * instead of Parts.*/
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

    /** Loads the inventory FXML file for the application, sets up the scene, and displays the primary stage.*/
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryController.class.getResource("inventory.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /** Generates a dialog box when no search results are found. Creating the specific look too multiple
     * attempts. The dialog box by default appears dated and not optimized to display meaningful content.*/
    private void searchAlert() {
        Alert searchError = new Alert(Alert.AlertType.INFORMATION);
        searchError.setTitle("No Search Results Found");
        searchError.setHeaderText(null);
        searchError.setContentText("Search was unable to locate a valid ID or Name" +
                "\nPlease verify your search criteria and try your search again.");
        searchError.showAndWait();
    }

    /** Generates a dialog box when no selection is made in the table to modify.
     * This was very straightforward after creating the searchAlert. Requiring a title and content text change. */
    private void noSelection() {
        Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
        noSelection.setTitle("No Selection");
        noSelection.setHeaderText(null);
        noSelection.setContentText("Please select an item from the table to modify.");
        noSelection.showAndWait();
    }

    /** Stores the search criteria in a string and checks for a matching name. If the search results are empty,
     * then the process moves towards looking for a matching id. If nothing is found after both searches
     * the alert appears. A try catch loop was added in case an unexpected value reaches the integer search. */
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

    /** Stores the search criteria in a string and checks for a matching name. If the search results are empty,
     * then the process moves towards looking for a matching id. If nothing is found after both searches
     * the alert appears. A try catch loop was added in case an unexpected value reaches the integer search.
     * I created the part search first and this was a duplication changing parts to products. */
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

    /** Loads the add product FXML file and form, sets up the scene, and displays the primary stage.*/
    @FXML
    private void addProductButton(ActionEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add-product.fxml")));
        Scene mainMenuScene = new Scene(mainMenuParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(mainMenuScene);
        appStage.show();
    }

    /** Checks for a selected Product from the table. If none is selected an alert appears,
     * otherwise it loads the modify product FXML file and form, sets up the scene, and displays the primary stage.
     * Initially the screen progressed regardless of any selection. I then realized I missed initializing modifyProduct
     * before the null check. */
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

    /** Loads the add part FXML file and form, sets up the scene, and displays the primary stage.*/
    @FXML
    private void addPartButton(ActionEvent event) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add-part.fxml")));
        Scene mainMenuScene = new Scene(mainMenuParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(mainMenuScene);
        appStage.show();
    }

    /** This method checks for a selected Part from the table. If none is selected an alert appears,
     * otherwise it loads the modify product FXML file and form, sets up the scene, and displays the primary stage.
     * Similar to the modifyProductButton I needed to incorporate the null check.*/
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

    /** Created an alert to confirm part deletion after the button is pressed.
     * I expanded the scope to include the part name in the dialog prompt. */
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

    /** Created an alert to confirm product deletion after the button is pressed.
     * I expanded the scope to include the product name in the dialog prompt. I also attempted to add
     * the Associated Part to the modify button instead of the delete button and could not figure out why the prompt
     * would not trigger with a size greater than 0. */
    @FXML
    private void deleteProductButton() {
        Product selectedProduct = allProductTable.getSelectionModel().getSelectedItem();

        if (selectedProduct.getAllAssociatedParts().size() != 0) {
            Alert associatedPartDetected = new Alert(Alert.AlertType.INFORMATION);
            associatedPartDetected.setTitle("Associated Part Detected");
            associatedPartDetected.setHeaderText(null);
            associatedPartDetected.setContentText("""
                    This product contains associated parts.\s

                    Please use the modify button to remove parts,
                    then save the changes to allow product deletion.""");
            associatedPartDetected.showAndWait();

        } else {
            Alert confirmProductDeletion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmProductDeletion.setTitle("Confirm Product Deletion");
            confirmProductDeletion.setHeaderText(null);
            confirmProductDeletion.setContentText("Would you like to delete the product " + (selectedProduct.getName()) + " ?" +
                    "\nThis action is final and cannot be undone.");

            Optional<ButtonType> result = confirmProductDeletion.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
            }
        }
    }

    /** On button press to Exit the application, the window closes.*/
    @FXML
    private void exitButtonClicked() {
        Platform.exit();
    }

    public static void main(String[] args) {
        launch();
    }

}