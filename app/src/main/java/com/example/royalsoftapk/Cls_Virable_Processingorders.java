package com.example.royalsoftapk;

public class Cls_Virable_Processingorders {
    int ID;
    int ItemID;
    String ItemAName;
    int ItemSubID;
    String ItemSubAName;
    String CustomerName;
    int CustomerID;
    String Date;
    int State;
    int Property;
    boolean IsChecked;
    int StartNumber;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public String getItemAName() {
        return ItemAName;
    }

    public void setItemAName(String itemAName) {
        ItemAName = itemAName;
    }

    public String getItemSubAName() {
        return ItemSubAName;
    }

    public void setItemSubAName(String itemSubAName) {
        ItemSubAName = itemSubAName;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public String getDate() {
        return Date;
    }

    public int getItemSubID() {
        return ItemSubID;
    }

    public void setItemSubID(int itemSubID) {
        ItemSubID = itemSubID;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public int getProperty() {
        return Property;
    }

    public void setProperty(int property) {
        Property = property;
    }

    public boolean isChecked() {
        return IsChecked;
    }

    public int getStartNumber() {
        return StartNumber;
    }

    public void setStartNumber(int startNumber) {
        StartNumber = startNumber;
    }

    public void setChecked(boolean checked) {
        IsChecked = checked;
    }
}
