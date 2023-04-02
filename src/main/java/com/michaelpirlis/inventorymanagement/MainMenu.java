package com.michaelpirlis.inventorymanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** The Main Menu class launches the main GUI of the Inventory Management system. */
public class MainMenu extends Application implements Initializable {

    /** This method loads the inventory FXML file for the application, sets up the scene, and displays the primary stage.*/
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryController.class.getResource("inventory.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /** Utilized to initialize prior to the start of the stage. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public static void main(String[] args) {
        launch(args);
    }

}