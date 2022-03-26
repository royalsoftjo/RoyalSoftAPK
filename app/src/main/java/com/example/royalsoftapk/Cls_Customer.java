package com.example.royalsoftapk;

import androidx.annotation.NonNull;

public class Cls_Customer {
    private int ID;
    private String AName;
    private int SalesManID;

    public Cls_Customer(int ID, String AName, int salesManID) {
        this.ID = ID;
        this.AName = AName;
        SalesManID = salesManID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAName() {
        return AName;
    }

    public void setAName(String AName) {
        this.AName = AName;
    }

    public int getSalesManID() {
        return SalesManID;
    }

    public void setSalesManID(int salesManID) {
        SalesManID = salesManID;
    }

    @NonNull
    @Override
    public String toString() {
        return AName;
    }
}
