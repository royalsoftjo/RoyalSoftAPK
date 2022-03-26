package com.example.royalsoftapk;

import android.graphics.Bitmap;

public class clsGet {
    public int ID;
    private int Status;
    private String Text1;
    private String Text2;
    private boolean AllowRandom;
    private boolean AllowAlternate;
    private int RouteOrder;
    private String DayOfWeek;
    private  int CustomerId;
    private Bitmap Picture;
    private boolean IsRandom;


    public clsGet(int ID, int status, String text1, String text2, boolean allowRandom, boolean allowAlternate, int routeOrder, String dayOfWeek, int customerId, Bitmap picture, boolean isRandom) {
        this.ID = ID;
        Status = status;
        Text1 = text1;
        Text2 = text2;
        AllowRandom = allowRandom;
        AllowAlternate = allowAlternate;
        RouteOrder = routeOrder;
        DayOfWeek = dayOfWeek;
        CustomerId = customerId;
        Picture = picture;
        IsRandom = isRandom;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getText1() {
        return Text1;
    }

    public void setText1(String text1) {
        Text1 = text1;
    }

    public String getText2() {
        return Text2;
    }

    public void setText2(String text2) {
        Text2 = text2;
    }

    public boolean isAllowRandom() {
        return AllowRandom;
    }

    public void setAllowRandom(boolean allowRandom) {
        AllowRandom = allowRandom;
    }

    public boolean isAllowAlternate() {
        return AllowAlternate;
    }

    public void setAllowAlternate(boolean allowAlternate) {
        AllowAlternate = allowAlternate;
    }

    public int getRouteOrder() {
        return RouteOrder;
    }

    public void setRouteOrder(int routeOrder) {
        RouteOrder = routeOrder;
    }

    public String getDayOfWeek() {
        return DayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        DayOfWeek = dayOfWeek;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public Bitmap getPicture() {
        return Picture;
    }

    public void setPicture(Bitmap picture) {
        Picture = picture;
    }

    public boolean isRandom() {
        return IsRandom;
    }

    public void setRandom(boolean random) {
        IsRandom = random;
    }
}
