package com.example.royalsoftapk;

public class Cls_SettelmentWay {
    private int ID;
    private String AName;


    public Cls_SettelmentWay( String AName,int ID) {
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
