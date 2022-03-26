package com.example.royalsoftapk;

import androidx.annotation.NonNull;

public class Cls_ItemsHistoryIncomeVoucher {
    private String ID;
    private String Name;
    private String MainBarcode;
    private String Qty;
    private int rowindex;
    private String PurchasePrice;
    public Cls_ItemsHistoryIncomeVoucher() {
        this.ID = ID;
        this.Name = Name;
        this.MainBarcode=MainBarcode;
        this.Qty=Qty;
        this.rowindex=rowindex;
        this.PurchasePrice=PurchasePrice;
    }

    public String getName() {
        return Name;
    }


    public void setName(String Name) {
       this.Name = Name;
    }
    public void setrowindex(int rowindex) {
        this.rowindex = rowindex;
    }
    public String getMainBarcode() {
        return MainBarcode;
    }

    public void setMainBarcode(String MainBarcode) {
        this.MainBarcode = MainBarcode;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public String getQty() {
        return Qty;
    }

    public int getrowindex() {
        return rowindex;
    }
    public void setQty(String Qty) {
        this.Qty = Qty;
    }

    public String getPurchasePrice() {
        return PurchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        PurchasePrice = purchasePrice;
    }

    @NonNull
    @Override
    public String toString() {
        return Name;
    }
}
