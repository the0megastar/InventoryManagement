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

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

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
            if (searchPart.getName().contains(partName)) {
                namedParts.add(searchPart);
            }
        }
        return namedParts;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = getAllProducts();

        for (Product searchProduct : allProducts) {
            if (searchProduct.getName().contains(productName)) {
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

        Part brakes = new Part(1, "Brakes", 12.99, 15, 1, 20) {};
        addPart(brakes);
        Part tires = new Part(2, "Tires", 14.99, 15, 1, 20) {};
        addPart(tires);
        Part rims = new Part(3, "Rims", 56.99, 15, 1, 20) {};
        addPart(rims);
        Part outsourced =  new Outsourced(4, "Outsourced", 99.99, 12, 1, 20, "Test");
        addPart(outsourced);
        Part inhouse = new InHouse(5, "InHouse", 99.99, 12, 1, 20, 146);
        addPart(inhouse);
    }

    public static void addProductData() {

        Product giantBicycle = new Product(1, "Giant Bicycle", 299.99, 15, 1, 20);
        addProduct(giantBicycle);
        Product scottBicycle = new Product(2, "Scott Bicycle", 199.99, 15, 1, 20);
        addProduct(scottBicycle);
        Product gtBicycle = new Product(3, "GT Bike", 99.99, 15, 1, 20);
        addProduct(gtBicycle);
    }

    static {
        addPartData();
        addProductData();
    }
}
