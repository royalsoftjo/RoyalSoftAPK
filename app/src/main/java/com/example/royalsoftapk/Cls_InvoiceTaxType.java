package com.example.royalsoftapk;

public class Cls_InvoiceTaxType {
    private int ID;
    private String AName;

    public Cls_InvoiceTaxType(int ID, String AName) {
        this.ID = ID;
        this.AName = AName;
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
}
