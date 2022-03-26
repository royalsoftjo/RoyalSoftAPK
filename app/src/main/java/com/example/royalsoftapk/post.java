package com.example.royalsoftapk;

public class post {
    private  String conn;
    private  String BranchId;
    private  String StoreId;
    private  String VendorId;
    private  String ReceiverName;
    private  String Note;
    private  String VoucherNumber;
    private  String VoucherDate;
    private  String Reference;
    private  String TransactionID;
    private  String CustomerId;
    private  String PaymentId;
    private  String SalesManID;
    private String Details;
    private  String Status;

    public post(String conn, String branchId, String storeId, String vendorId, String receiverName, String note, String voucherNumber, String voucherDate, String reference, String transactionID, String customerId, String paymentId, String salesManID, String details) {
        this.conn = conn;
        BranchId = branchId;
        StoreId = storeId;
        VendorId = vendorId;
        ReceiverName = receiverName;
        Note = note;
        VoucherNumber = voucherNumber;
        VoucherDate = voucherDate;
        Reference = reference;
        TransactionID = transactionID;
        CustomerId = customerId;
        PaymentId = paymentId;
        SalesManID = salesManID;
        Details = details;
    }

    public String getStatus() {
        return Status;
    }

    public void setConn(String conn) {
        this.conn = conn;
    }

    public void setBranchId(String branchId) {
        BranchId = branchId;
    }


    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public void setVendorId(String vendorId) {
        VendorId = vendorId;
    }


    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }



    public void setNote(String note) {
        Note = note;
    }

    public void setVoucherNumber(String voucherNumber) {
        VoucherNumber = voucherNumber;
    }


    public void setVoucherDate(String voucherDate) {
        VoucherDate = voucherDate;
    }


    public void setReference(String reference) {
        Reference = reference;
    }


    public void setTransactionID(String transactionID) {
        TransactionID = transactionID;
    }


    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }


    public void setPaymentId(String paymentId) {
        PaymentId = paymentId;
    }


    public void setSalesManID(String salesManID) {
        SalesManID = salesManID;
    }

    public void setDetails(String details) {
        Details = details;
    }

}
