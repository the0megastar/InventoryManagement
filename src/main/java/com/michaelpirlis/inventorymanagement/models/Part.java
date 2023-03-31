package com.michaelpirlis.inventorymanagement.models;

/** Supplied class Part.java which defines the abstract class Part. Including getters and setters. */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Collects the Part id. */
    public int getId() {
        return id;
    }

    /** Sets the Part id. */
    public void setId(int id) {
        this.id = id;
    }

    /** Collects the Part name. */
    public String getName() {
        return name;
    }

    /** Sets the Part name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Collects the Part price. */
    public double getPrice() {
        return price;
    }

    /** Sets the Part price. */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Collects the Part stock quantity. */
    public int getStock() {
        return stock;
    }

    /** Sets the Part stock quantity. */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Collects the Part minimum inventory quantity. */
    public int getMin() {
        return min;
    }

    /** Sets the Part minimum inventory quantity. */
    public void setMin(int min) {
        this.min = min;
    }

    /** Collects the Part maximum inventory quantity. */
    public int getMax() {
        return max;
    }

    /** Sets the Part maximum inventory quantity. */
    public void setMax(int max) {
        this.max = max;
    }

}
