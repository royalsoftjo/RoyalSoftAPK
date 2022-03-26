package com.example.royalsoftapk;

import androidx.annotation.NonNull;

public class Cls_getItems {
    private int ID;
    private String AName;
    private String MainBarcode;

    public Cls_getItems(int ID, String AName,String MainBarcode) {
        this.ID = ID;
        this.AName = AName;
        this.MainBarcode=MainBarcode;
    }

    public String getMainBarcode() {
        return MainBarcode;
    }

    public void setMainBarcode(String mainBarcode) {
        MainBarcode = mainBarcode;
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

    @NonNull
    @Override
    public String toString() {
        return AName;
    }
}
