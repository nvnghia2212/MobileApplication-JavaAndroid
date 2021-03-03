package com.example.nghia.maplocation;

public class ChatGroup {

    private int Id;
    private String MemberSend;
    private String TextSend;
    private String DateMess;
    private String TimeMess;
    private String NameGroup;
    private String UserAdd;
    private String DateTimeAdd;

    public ChatGroup(int id, String memberSend, String textSend, String dateMess, String timeMess, String nameGroup, String userAdd, String dateTimeAdd) {
        Id = id;
        MemberSend = memberSend;
        TextSend = textSend;
        DateMess = dateMess;
        TimeMess = timeMess;
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

    public String getMemberSend() {
        return MemberSend;
    }

    public void setMemberSend(String memberSend) {
        MemberSend = memberSend;
    }

    public String getTextSend() {
        return TextSend;
    }

    public void setTextSend(String textSend) {
        TextSend = textSend;
    }

    public String getDateMess() {
        return DateMess;
    }

    public void setDateMess(String dateMess) {
        DateMess = dateMess;
    }

    public String getTimeMess() {
        return TimeMess;
    }

    public void setTimeMess(String timeMess) {
        TimeMess = timeMess;
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
