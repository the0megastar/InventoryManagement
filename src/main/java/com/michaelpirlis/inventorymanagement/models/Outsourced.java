package com.michaelpirlis.inventorymanagement.models;

/**
 * @author Michael Pirlis
 * √ private companyName:String
 * √ public Outsourced(id:int, name:String, price:double, stock:int, min:int, max:int, companyName:String)
 * √ public setCompanyName(companyName:String):void
 * √ public getCompanyName():String
 */

public class Outsourced extends Part{
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @param companyName the name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }
}
