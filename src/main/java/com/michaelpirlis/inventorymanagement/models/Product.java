package com.michaelpirlis.inventorymanagement.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Michael Pirlis
 * √ private associatedParts:ObservableList<Part>
 * √ private id:int
 * √ private name:String
 * √ private price:double
 * √ private stock:int
 * √ private min:int
 * √ private max:int
 * √ public Product(id:int, name:String, price:double, stock:int, min:int, max:int)
 * √ public addAssociatedPart(part:Part):void
 * √ public deleteAssociatedPart(selectedAssociatedPart:Part):boolean
 * √ getAllAssociatedParts():ObservableList<Part>
 */

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

//    public Product () {
//        this(0, "test name", 1.00, 1, 1, 20);
////        product.addAssociatedPart(selectedItem);
//    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        return associatedParts.remove(selectedAssociatedPart);
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}

