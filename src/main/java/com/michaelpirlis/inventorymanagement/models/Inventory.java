package com.michaelpirlis.inventorymanagement.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Objects;

/** Defines the Inventory class which predominantly collects all Parts and Products.
 * Also includes updating and removing the Parts and Products from the corresponding Array Lists. */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Add a selected Part to the Observable Array List. */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** Add a selected Product to the Observable Array List. */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** Lookup the selected Part using the part id to proceed through the Array List.
     * All lookup fields went very well thanks to the coding example with the Web blast video with Mark.*/
    public static ObservableList<Part> lookupPart(int partId) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();

        for (Part searchPart : allParts) {
            if (Objects.equals(searchPart.getId(), partId)) {
                namedParts.add(searchPart);
            }
        }
        return namedParts;
    }

    /** Lookup the selected Product using the part id to proceed through the Array List. */
    public static ObservableList<Product> lookupProduct(int productId) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = getAllProducts();

        for (Product searchProduct : allProducts) {
            if (Objects.equals(searchProduct.getId(), productId)) {
                namedProducts.add(searchProduct);
            }
        }
        return namedProducts;
    }

    /** Lookup the selected Part by names using the string to proceed through the Array List.
     * This method encountered the issue of being case-sensitive. Adding toLowerCase() corrected the behavior. */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();

        for (Part searchPart : allParts) {
            if (searchPart.getName().toLowerCase().contains(partName)) {
                namedParts.add(searchPart);
            }
        }

        return namedParts;
    }

    /** Lookup the selected Product by names using the string to proceed through the Array List.
     * This method encountered the issue of being case-sensitive. Adding toLowerCase() corrected the behavior. */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = getAllProducts();

        for (Product searchProduct : allProducts) {
            if (searchProduct.getName().toLowerCase().contains(productName)) {
                namedProducts.add(searchProduct);
            }
        }
        return namedProducts;
    }

    /** Updates the selected Part by using the index number and replacing the previous value stored. */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /** Updates the selected Product by using the index number and replacing the previous value stored. */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /** Removes a selected Part from the Observable Array List */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /** Removes a selected Product from the Observable Array List */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    /** Collects all Parts from the Observable Array List */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** Collects all Products from the Observable Array List */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** In House and Outsourced part data for testing. */
    public static void addPartData() {

        addPart(new InHouse(1,"Brakes", 12.99, 15, 1, 20, 146));
        addPart(new InHouse(2, "Tires", 14.99, 15, 1, 20, 144));
        addPart(new InHouse(3, "Rims", 56.99, 15, 1, 20, 142));
        addPart(new Outsourced(4, "Calipers", 99.99, 12, 1, 20, "BMX"));
    }

    /** Product data for testing. */
    public static void addProductData() {

        addProduct(new Product(1, "Mountain Bicycle", 299.99, 15, 1, 20));
        addProduct(new Product(2, "Scott Bicycle", 199.99, 15, 1, 20));
        addProduct(new Product(3, "GT Bike", 99.99, 15, 1, 20));
    }

    static {
        addPartData();
        addProductData();
    }
}
