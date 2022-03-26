package com.example.royalsoftapk;

import androidx.annotation.NonNull;

public class ClsTypeDevices {
    private int ID;
    private String TypeName;

    public ClsTypeDevices( String typeName,int ID) {
        this.ID = ID;
        TypeName = typeName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    @NonNull
    @Override
    public String toString() {
        return TypeName;
    }
}
