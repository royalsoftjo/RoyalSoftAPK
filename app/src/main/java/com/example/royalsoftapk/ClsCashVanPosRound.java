package com.example.royalsoftapk;

public class ClsCashVanPosRound {
    public String conn ;
    public int CustomerID ;
    public int EmpID;
    public String LocationImage;
    public int BatteryPercentage;
    public String CustomerSignature ;
    public String locationOnMap ;
    public int CreationUserID ;
    public String CreationDate ;
    public int routeDetailsID ;
    public int RouteStatus ;
    public String Notes ;
    public String Status ;

    public ClsCashVanPosRound(String conn, int customerID, int empID, String locationImage, int batteryPercentage, String customerSignature, String locationOnMap, int creationUserID, String creationDate, int routeDetailsID, int routeStatus, String notes) {
        this.conn = conn;
        CustomerID = customerID;
        EmpID = empID;
        LocationImage = locationImage;
        BatteryPercentage = batteryPercentage;
        CustomerSignature = customerSignature;
        this.locationOnMap = locationOnMap;
        CreationUserID = creationUserID;
        CreationDate = creationDate;
        this.routeDetailsID = routeDetailsID;
        RouteStatus = routeStatus;
        Notes = notes;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
