package com.example.royalsoftapk;

public class Post_OrderDetails {
    private  String conn;
    private  int OrderID;
    private  int BranchId;
    private  int StoreID;
    private  int StationID;
    private  String  WorkDayDate;
    private  int  WorkDayID;
    private  int  SessionID;
    private  int  SectionID;
    private  int  TableID;
    private  int UserID;
    private  int  WaiterID;
    private  String  Details;
    private  String Status;
    public Post_OrderDetails(String conn,int OrderID,
            int BranchId,
            int StoreID,
            int StationID,
            String  WorkDayDate,
            int  WorkDayID,
            int  SessionID,
            int  SectionID,
            int  TableID,
            int  UserID,
            int  WaiterID,
            String  Details)
    {
        this.conn=conn;
        this. OrderID=OrderID;
        this. BranchId=BranchId;
        this. StoreID=StoreID;
        this. StationID=StationID;
        this.  WorkDayDate=WorkDayDate;
        this.  WorkDayID=WorkDayID;
        this.  SessionID=SessionID;
        this.  SectionID=SectionID;
        this.  TableID=TableID;
        this.  UserID=UserID;
        this.  WaiterID=WaiterID;
        this.  Details=Details;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
