package com.example.royalsoftapk;

import androidx.annotation.NonNull;

public class ClsOrderSalesSelected {
    private int RowIndex=0;
    private String ID;
    private String MainBarcode;
    private String ItemName;
    private String Qty;
    private String Price;
    private  String Total;
    private  Boolean IsHasAdditions;
    private  Boolean IsHasOptions;
    private  Boolean IsHasNotes;
    private  Boolean IsUpdatedQty;
    private  int AdditionID;
    private  int TaxID;
    private  int Status=0;
    private  Boolean IsTax;
    private Boolean IsItemAdditions;
    private Boolean IsItemNotes;
    private Boolean IsItemOptions;
    private String Notes;
    private double QtyBefore;
    private double BasedQTY;
    private double EmptyQTY;
    private String  Type_panier;
    private int numberbaskets;
    private boolean IsDelete;
    private boolean IsNumber;
    private boolean IsHasBasket;
    private int BasketTypeID;
    private String BasketTypeName;
    private double BasketTypeWeight;
    private double SalesPriceBeforeTax;
    private double TaxAmount;
    private double TotalBeforeTax;
    private double TotalTaxAmount;
    private int SalesTaxID;
    private boolean IsVolatilePrices;
    private String CreationDate;
    private double WholeSalePrice;
    private double MinWholeSalePrice;
    private double TaxRatio;

    public ClsOrderSalesSelected(String ID, String MainBarcode, String ItemName, String Qty, String Price, String Total,double WholeSalePrice,double MinWholeSalePrice,boolean IsNumber,double TaxRatio) {
        this.ID = ID;
        this.MainBarcode=MainBarcode;
        this.ItemName=ItemName;
        this.Qty=Qty;
        this.Price=Price;
        this.Total = Total;
        this.MinWholeSalePrice=MinWholeSalePrice;
        this.WholeSalePrice=WholeSalePrice;
        this.IsNumber=IsNumber;
        this.TaxRatio=TaxRatio;
    }

    public boolean isVolatilePrices() {
        return IsVolatilePrices;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public void setVolatilePrices(boolean volatilePrices) {
        IsVolatilePrices = volatilePrices;
    }

    public int getSalesTaxID() {
        return SalesTaxID;
    }

    public void setSalesTaxID(int salesTaxID) {
        SalesTaxID = salesTaxID;
    }

    public double getTotalBeforeTax() {
        return TotalBeforeTax;
    }

    public void setTotalBeforeTax(double totalBeforeTax) {
        TotalBeforeTax = totalBeforeTax;
    }

    public double getTotalTaxAmount() {
        return TotalTaxAmount;
    }

    public void setTotalTaxAmount(double totalTaxAmount) {
        TotalTaxAmount = totalTaxAmount;
    }

    public double getSalesPriceBeforeTax() {
        return SalesPriceBeforeTax;
    }

    public void setSalesPriceBeforeTax(double salesPriceBeforeTax) {
        SalesPriceBeforeTax = salesPriceBeforeTax;
    }

    public boolean isNumber() {
        return IsNumber;
    }

    public double getTaxRatio() {
        return TaxRatio;
    }

    public void setTaxRatio(double taxRatio) {
        TaxRatio = taxRatio;
    }

    public double getTaxAmount() {
        return TaxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        TaxAmount = taxAmount;
    }

    public String getBasketTypeName() {
        return BasketTypeName;
    }

    public void setBasketTypeName(String basketTypeName) {
        BasketTypeName = basketTypeName;
    }

    public double getBasketTypeWeight() {
        return BasketTypeWeight;
    }

    public void setBasketTypeWeight(double basketTypeWeight) {
        BasketTypeWeight = basketTypeWeight;
    }

    public int getBasketTypeID() {
        return BasketTypeID;
    }

    public void setBasketTypeID(int basketTypeID) {
        BasketTypeID = basketTypeID;
    }

    public double getEmptyQTY() {
        return EmptyQTY;
    }

    public void setEmptyQTY(double emptyQTY) {
        EmptyQTY = emptyQTY;
    }
    public String getType_panier() {
        return Type_panier;
    }

    public void setType_panier(String type_panier) {
        Type_panier = type_panier;
    }

    public int getNumberbaskets() {
        return numberbaskets;
    }

    public void setNumberbaskets(int numberbaskets) {
        this.numberbaskets = numberbaskets;
    }

    public double getBasedQTY() {
        return BasedQTY;
    }

    public void setBasedQTY(double basedQTY) {
        BasedQTY = basedQTY;
    }

    public boolean getHasBasket() {
        return IsHasBasket;
    }

    public void setHasBasket(boolean hasBasket) {
        IsHasBasket = hasBasket;
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
        return Global.convertToEnglish(Qty);
    }

    public void setQty(String qty) {
        Qty = Global.convertToEnglish(qty);
    }

    public String getPrice() {
        return Global.convertToEnglish(Price);
    }

    public void setPrice(String price) {
        Price = Global.convertToEnglish(price);
    }

    public String getTotal() {
        return Global.convertToEnglish(Total);
    }

    public void setTotal(String total) {
        Total = Global.convertToEnglish(total);
    }

    public Boolean getHasAdditions() {
        return IsHasAdditions;
    }

    public void setHasAdditions(Boolean hasAdditions) {
        IsHasAdditions = hasAdditions;
    }

    public Boolean getHasOptions() {
        return IsHasOptions;
    }

    public void setHasOptions(Boolean hasOptions) {
        IsHasOptions = hasOptions;
    }

    public int getAdditionID() {
        return AdditionID;
    }

    public void setAdditionID(int additionID) {
        AdditionID = additionID;
    }

    public int getRowIndex() {
        return RowIndex;
    }

    public void setRowIndex(int rowIndex) {
        RowIndex = rowIndex;
    }

    public Boolean getHasNotes() {
        return IsHasNotes;
    }

    public void setHasNotes(Boolean notes) {
        IsHasNotes = notes;
    }

    public Boolean getUpdatedQty() {
        return IsUpdatedQty;
    }

    public void setUpdatedQty(Boolean updatedQty) {
        IsUpdatedQty = updatedQty;
    }

    public int getTaxID() {
        return TaxID;
    }

    public void setTaxID(int taxID) {
        TaxID = taxID;
    }

    public Boolean getTax() {
        return IsTax;
    }

    public void setTax(Boolean tax) {
        IsTax = tax;
    }

    public Boolean getItemAdditions() {
        return IsItemAdditions;
    }

    public void setItemAdditions(Boolean itemAdditions) {
        IsItemAdditions = itemAdditions;
    }

    public Boolean getItemNotes() {
        return IsItemNotes;
    }

    public void setItemNotes(Boolean itemNotes) {
        IsItemNotes = itemNotes;
    }

    public Boolean getItemOptions() {
        return IsItemOptions;
    }

    public void setItemOptions(Boolean itemOptions) {
        IsItemOptions = itemOptions;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public double getQtyBefore() {
        return QtyBefore;
    }

    public void setQtyBefore(double qtyBefore) {
        QtyBefore = qtyBefore;
    }

    public boolean getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(boolean isDelete) {
        IsDelete = isDelete;
    }

    public boolean getisNumber() {
        return IsNumber;
    }

    public void setNumber(boolean number) {
        IsNumber = number;
    }

    public int getStatus() {
        return Status;
    }

    public double getWholeSalePrice() {
        return WholeSalePrice;
    }

    public void setWholeSalePrice(double wholeSalePrice) {
        WholeSalePrice = wholeSalePrice;
    }

    public double getMinWholeSalePrice() {
        return MinWholeSalePrice;
    }

    public void setMinWholeSalePrice(double minWholeSalePrice) {
        MinWholeSalePrice = minWholeSalePrice;
    }

    public void setStatus(int status) {
        Status = status;
    }

    @NonNull
    @Override
    public String toString() {
        return ItemName+"    ------------  " +" ↓ "+ String.valueOf(MinWholeSalePrice) + " ~ "+String.valueOf(Price)+" ↑ ";
    }
}
