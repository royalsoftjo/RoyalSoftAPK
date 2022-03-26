package com.example.royalsoftapk;

import android.graphics.Bitmap;

public class Cls_ItemsInvoiceSales {
    private Bitmap base64Image;

    private String ID;
    private String MainBarcode;
    private String ItemName;
    private String Qty;
    private String Price;
    private String CategoryID;

    public Cls_ItemsInvoiceSales(Bitmap base64Image, String ID, String MainBarcode, String ItemName, String Qty, String Price) {
        this.base64Image = base64Image;
        this.ID = ID;
        this.MainBarcode=MainBarcode;
        this.ItemName=ItemName;
        this.Qty=Qty;
        this.Price=Price;
    }

    public Bitmap getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(Bitmap base64Image) {
        this.base64Image = base64Image;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMainBarcode() {
        return MainBarcode;
    }

    public void setMainBarcode(String mainBarcode) {
        MainBarcode = mainBarcode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }
}
