package com.example.nghia.maplocation;

public class Dating {
    private int Id;
    private String NameAddress;
    private String DateAddress;
    private String DateTimeAdd;
    private String UserAdd;

    public Dating(int id, String nameAddress, String dateAddress, String dateTimeAdd, String userAdd) {
        Id = id;
        NameAddress = nameAddress;
        DateAddress = dateAddress;
        DateTimeAdd = dateTimeAdd;
        UserAdd = userAdd;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNameAddress() {
        return NameAddress;
    }

    public void setNameAddress(String nameAddress) {
        NameAddress = nameAddress;
    }

    public String getDateAddress() {
        return DateAddress;
    }

    public void setDateAddress(String dateAddress) {
        DateAddress = dateAddress;
    }

    public String getDateTimeAdd() {
        return DateTimeAdd;
    }

    public void setDateTimeAdd(String dateTimeAdd) {
        DateTimeAdd = dateTimeAdd;
    }

    public String getUserAdd() {
        return UserAdd;
    }

    public void setUserAdd(String userAdd) {
        UserAdd = userAdd;
    }
}
