package com.example.royalsoftapk;

import androidx.annotation.NonNull;

public class Cls_StaionID_Spinner {

    private String StaionID;
    private String IDStaionID;

    public Cls_StaionID_Spinner(String staionID, String IDStaionID) {
        StaionID = staionID;
        this.IDStaionID = IDStaionID;
    }

    public String getStaionID() {
        return StaionID;
    }

    public void setStaionID(String staionID) {
        StaionID = staionID;
    }

    public String getIDStaionID() {
        return IDStaionID;
    }

    public void setIDStaionID(String IDStaionID) {
        this.IDStaionID = IDStaionID;
    }

    @NonNull
    @Override
    public String toString() {
        return StaionID;
    }
}
