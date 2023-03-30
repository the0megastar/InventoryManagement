module com.michaelpirlis.inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.michaelpirlis.inventorymanagement to javafx.fxml;
    exports com.michaelpirlis.inventorymanagement;
    opens com.michaelpirlis.inventorymanagement.models to javafx.fxml;
    exports com.michaelpirlis.inventorymanagement.models;
}