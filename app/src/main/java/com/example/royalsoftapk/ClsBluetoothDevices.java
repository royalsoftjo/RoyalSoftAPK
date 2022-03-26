package com.example.royalsoftapk;

public class ClsBluetoothDevices {
    private int ID;
    private String DevicesName;
    private int TypeId;
    private String TypeName;
    private String MacAddress;
    private String DateCreation;

    public ClsBluetoothDevices(int ID, String devicesName, int typeId, String typeName, String macAddress, String dateCreation) {
        this.ID = ID;
        DevicesName = devicesName;
        TypeId = typeId;
        TypeName = typeName;
        MacAddress = macAddress;
        DateCreation = dateCreation;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public int getTypeId() {
        return TypeId;
    }

    public void setTypeId(int typeId) {
        TypeId = typeId;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDevicesName() {
        return DevicesName;
    }

    public void setDevicesName(String devicesName) {
        DevicesName = devicesName;
    }

    public String getMacAddress() {
        return MacAddress;
    }

    public void setMacAddress(String macAddress) {
        MacAddress = macAddress;
    }

    public String getDateCreation() {
        return DateCreation;
    }

    public void setDateCreation(String dateCreation) {
        DateCreation = dateCreation;
    }
}
