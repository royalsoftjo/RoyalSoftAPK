package com.example.royalsoftapk;

import androidx.annotation.NonNull;

public class Cls_Store_Spinner {
    private String ID;
    private String Name;

    public Cls_Store_Spinner(String Name, String ID) {
        this.Name = Name;
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        Name = Name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @NonNull
    @Override
    public String toString() {
        return Name;
    }
}
