package com.example.royalsoftapk;

public class post_SaveCashVan {
    private  String conn;
    private String signature;
    private String imgg;
    private  int InvoiceNo;
    private  int TaxType;
    private  int StoreId;
    private  int SettelmentWayId;
    private  String SettelmentWayName;
    private  String InvoiceTypeName;
    private  int  CustomerId;
    private  String  CustomerName;
    private  int  SalesManID;
    private  int  CashId;
    private  String  Notes;
    private  double  TotalQty;
    private  int  LoginBranchID;
    private  String  LoginBranchName;
    private  String  LoginStoreName;
    private  int  LoginUserID;
    private  String  LoginUserName;
    private  double  TotalBeforeTax;
    private  double  TotalTaxAmount;
    private  double  TotalAfterTax;
    private  String  Details;
    private  String  DataSourceName;
    private  String Status;
    private  String PohtoBase64;
    private  String InvoiceNoBefore;
    private int TransType;

    public post_SaveCashVan(String conn, String signature, String imgg, int invoiceNo, int taxType, int storeId, int settelmentWayId, String settelmentWayName, String invoiceTypeName, int customerId, String customerName, int salesManID, int cashId, String notes, double totalQty, int loginBranchID, String loginBranchName, String loginStoreName, int loginUserID, String loginUserName, double totalBeforeTax, double totalTaxAmount, double totalAfterTax,int TransType, String details,String DataSourceName) {
        this.conn = conn;
        this.signature = signature;
        this.imgg = imgg;
        InvoiceNo = invoiceNo;
        TaxType = taxType;
        StoreId = storeId;
        SettelmentWayId = settelmentWayId;
        SettelmentWayName = settelmentWayName;
        InvoiceTypeName = invoiceTypeName;
        CustomerId = customerId;
        CustomerName = customerName;
        SalesManID = salesManID;
        CashId = cashId;
        Notes = notes;
        TotalQty = totalQty;
        LoginBranchID = loginBranchID;
        LoginBranchName = loginBranchName;
        LoginStoreName = loginStoreName;
        LoginUserID = loginUserID;
        LoginUserName = loginUserName;
        TotalBeforeTax = totalBeforeTax;
        TotalTaxAmount = totalTaxAmount;
        TotalAfterTax = totalAfterTax;
        this.TransType=TransType;
        Details = details;
        this.DataSourceName=DataSourceName;
    }

    public String getInvoiceNoBefore() {
        return InvoiceNoBefore;
    }

    public void setInvoiceNoBefore(String invoiceNoBefore) {
        InvoiceNoBefore = invoiceNoBefore;
    }

    public String getPohtoBase64() {
        return PohtoBase64;
    }

    public void setPohtoBase64(String pohtoBase64) {
        PohtoBase64 = pohtoBase64;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
