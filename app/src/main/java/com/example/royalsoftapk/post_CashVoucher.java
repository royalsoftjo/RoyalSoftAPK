package com.example.royalsoftapk;

public class post_CashVoucher {
    private String conn;
    private String dtpVoucherDate;
    private int BranchID;
    private int CostCenterID;
    private int RevenuCenterID;
    private int ProfitCenterID;
    private double Amount;
    private String Notes;
    private int MemoID;
    private int LoginUserID;
    private int VoucherManualNumber;
    private String Receiver;
    private int CustomerID;
    private int CashID;
    private int transactionID;
    private  String Status;

    public post_CashVoucher(String conn,String dtpVoucherDate, int branchID, int costCenterID, int revenuCenterID, int profitCenterID, double amount, String notes, int memoID, int loginUserID, int VoucherManualNumber, String receiver, int customerID, int cashID,int transactionID) {
       this.conn=conn;
        this.dtpVoucherDate = dtpVoucherDate;
        BranchID = branchID;
        CostCenterID = costCenterID;
        RevenuCenterID = revenuCenterID;
        ProfitCenterID = profitCenterID;
        Amount = amount;
        Notes = notes;
        MemoID = memoID;
        LoginUserID = loginUserID;
        this.VoucherManualNumber = VoucherManualNumber;
        Receiver = receiver;
        CustomerID = customerID;
        CashID = cashID;
        this.transactionID = transactionID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
