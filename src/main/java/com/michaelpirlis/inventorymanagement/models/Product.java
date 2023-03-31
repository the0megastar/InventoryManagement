package com.michaelpirlis.inventorymanagement.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This defines the Product class including all getters and setters. */
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

    /** Collects the Product id. */
    public int getId() {
        return id;
    }

    /** Sets the Product id. */
    public void setId(int id) {
        this.id = id;
    }

    /** Collects the Product name. */
    public String getName() {
        return name;
    }

    /** Sets the Product name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Collects the Product price. */
    public double getPrice() {
        return price;
    }

    /** Sets the Product price. */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Collects the Product stock quantity. */
    public int getStock() {
        return stock;
    }

    /** Sets the Product stock quantity. */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Collects the Product minimum inventory quantity. */
    public int getMin() {
        return min;
    }

    /** Sets the Product minimum inventory quantity. */
    public void setMin(int min) {
        this.min = min;
    }

    /** Collects the Product maximum inventory quantity. */
    public int getMax() {
        return max;
    }

    /** Sets the Product maximum inventory quantity. */
    public void setMax(int max) {
        this.max = max;
    }

    /** Adds associated parts to the Product. */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** Removes associated parts to the Product. */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        return associatedParts.remove(selectedAssociatedPart);
    }

    /** Collects all associated parts to the Product. */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}

