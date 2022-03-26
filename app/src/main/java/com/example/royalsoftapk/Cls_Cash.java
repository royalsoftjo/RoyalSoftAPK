package com.example.royalsoftapk;

import androidx.annotation.NonNull;

public class Cls_Cash {
    private int ID;
    private String AName;

    public Cls_Cash(int ID, String AName) {
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

    @NonNull
    @Override
    public String toString() {
        return AName;
    }
}
