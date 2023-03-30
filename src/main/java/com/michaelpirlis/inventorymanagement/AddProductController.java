package com.michaelpirlis.inventorymanagement;

import com.michaelpirlis.inventorymanagement.models.Inventory;
import com.michaelpirlis.inventorymanagement.models.Part;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.michaelpirlis.inventorymanagement.models.Inventory.lookupPart;
import static com.michaelpirlis.inventorymanagement.models.Product.*;

public class AddProductController extends Application implements Initializable {
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


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddProductController.class.getResource("add-product.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void initialize(URL location, ResourceBundle resources) {

        InventoryController.partTableSetup(allPartTable, partIdColumn, partNameColumn, partInventoryColumn, partPriceColumn);
        associatedPartTable();
        // allProductTable.setItems(Inventory.getAllProducts());
    }

    public void associatedPartTable() {
        associatedIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedTable.setItems(getAllAssociatedParts());
        //        associatedTable.setVisible(true);
    }

    @FXML
    public void addAssociatedButton() {
        Part selectedPart = allPartTable.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            try {
                addAssociatedPart(selectedPart);
                allPartTable.getSelectionModel().clearSelection();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void removeAssociatedButton() {
        Part selectedPart = associatedTable.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            try {
                deleteAssociatedPart(selectedPart);
                associatedTable.getSelectionModel().clearSelection();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void searchAlert() {
        Alert searchError = new Alert(Alert.AlertType.INFORMATION);
        searchError.setTitle("No Search Results Found");
        searchError.setHeaderText(null);
        searchError.setContentText("Search was unable to locate a valid ID or Name" +
                "\nPlease verify your search criteria and try your search again.");
        searchError.showAndWait();
    }

    @FXML
    public void searchParts() {
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
                // ignore
            }

            if (searchList.isEmpty()) {
                searchAlert();
                allPartTable.setItems(Inventory.getAllParts());
            }
        }

        partSearch.clear();
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

