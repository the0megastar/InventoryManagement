package com.michaelpirlis.inventorymanagement.models;

/** This defines Outsourced Parts that extends and inherits the Part class, including all getters and setters. */
public class Outsourced extends Part{
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Sets the Company Name for Outsourced parts. */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** Collects the Company Name for Outsourced parts. */
    public String getCompanyName() {
        return companyName;
    }
}
