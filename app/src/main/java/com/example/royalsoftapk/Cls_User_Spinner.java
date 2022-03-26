package com.example.royalsoftapk;

import androidx.annotation.NonNull;

public class Cls_User_Spinner {

   private String UserNameEmp;
    private String IDUserNameEmp;

    public Cls_User_Spinner(String userNameEmp, String IDUserNameEmp) {
        UserNameEmp = userNameEmp;
        this.IDUserNameEmp = IDUserNameEmp;
    }

    public String getUserNameEmp() {
        return UserNameEmp;
    }

    public void setUserNameEmp(String userNameEmp) {
        UserNameEmp = userNameEmp;
    }

    public String getIDUserNameEmp() {
        return IDUserNameEmp;
    }

    public void setIDUserNameEmp(String IDUserNameEmp) {
        this.IDUserNameEmp = IDUserNameEmp;
    }

    @NonNull
    @Override
    public String toString() {
        return UserNameEmp;
    }
}
