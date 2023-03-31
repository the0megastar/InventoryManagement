package com.michaelpirlis.inventorymanagement.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Objects;

/**
 * @author Michael Pirlis
 * √ private allParts:ObservableList<Part>
 * √ private allProducts:ObservableList<Product>
 * √ public addPart(newPart:Part):void
 * √ public addProduct(newProduct:Product):void
 * √ public lookupPart(partId:int):Part
 * √ public lookupProduct(productId:int):Product
 * √ public lookupPart(partName:String):ObservableList<Part>
 * √ public lookupProduct(productName:String):ObservableList<Part>
 * √ public updatePart(index:int, selectedPart:Part):void
 * √ public updateProduct(index:int, selectedProduct:Product):void
 * √ public deletePart(selectedPart:Part):boolean
 * √ public deleteProduct(selectedProduct:Product):boolean
 * √ public getAllParts():ObservableList<Part>
 * √ public getAllProducts():ObservableList<Product>
 */

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

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

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static void addPartData() {

        addPart(new InHouse(1,"Brakes", 12.99, 15, 1, 20, 146));
        addPart(new InHouse(2, "Tires", 14.99, 15, 1, 20, 144));
        addPart(new InHouse(3, "Rims", 56.99, 15, 1, 20, 142));
        addPart(new Outsourced(4, "Calipers", 99.99, 12, 1, 20, "BMX"));
    }

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
