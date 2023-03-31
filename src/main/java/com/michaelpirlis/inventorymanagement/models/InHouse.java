package com.michaelpirlis.inventorymanagement.models;

/** This defines In House Parts that extends and inherits the Part class, including all getters and setters. */
public class InHouse extends Part {
    private int machineID;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineID = machineId;
    }

    /** Sets the Machine id for In House parts. */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /** Collects the Machine id for In House parts. */
    public int getMachineID() {
        return machineID;
    }
}
