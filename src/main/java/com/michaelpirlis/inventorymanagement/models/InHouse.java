package com.michaelpirlis.inventorymanagement.models;

/**
 * @author Michael Pirlis
 * √ private machineId:int
 * √ public InHouse(id:int, name:String, price:double, stock:int, min:int, max:int, machineId:int)
 * √ public setMachineId(machineId:int):void
 * √ public getMachineId():int
 */


public class InHouse extends Part {
    private int machineID;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineID = machineId;
    }

    /**
     * @param machineID set the machineID
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /**
     * @return the machineID
     */
    public int getMachineID() {
        return machineID;
    }
}
