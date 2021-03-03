package com.example.nghia.maplocation;

public class Group {

    private int Id;
    private String NameGroup;
    private String UserAdd;
    private String DateTimeAdd;

    public Group(int id, String nameGroup, String userAdd, String dateTimeAdd) {
        Id = id;
        NameGroup = nameGroup;
        UserAdd = userAdd;
        DateTimeAdd = dateTimeAdd;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNameGroup() {
        return NameGroup;
    }

    public void setNameGroup(String nameGroup) {
        NameGroup = nameGroup;
    }

    public String getUserAdd() {
        return UserAdd;
    }

    public void setUserAdd(String userAdd) {
        UserAdd = userAdd;
    }

    public String getDateTimeAdd() {
        return DateTimeAdd;
    }

    public void setDateTimeAdd(String dateTimeAdd) {
        DateTimeAdd = dateTimeAdd;
    }
}
