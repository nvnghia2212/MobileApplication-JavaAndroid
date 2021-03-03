package com.example.nghia.maplocation;

public class SendMessenger {
    private int Id;
    private String EmailSend;
    private String TextMess;
    private String EmailReceived;
    private String DateMess;
    private String TimeMess;

    public SendMessenger(int id, String emailSend, String textMess, String emailReceived, String dateMess, String timeMess) {
        Id = id;
        EmailSend = emailSend;
        TextMess = textMess;
        EmailReceived = emailReceived;
        DateMess = dateMess;
        TimeMess = timeMess;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getEmailSend() {
        return EmailSend;
    }

    public void setEmailSend(String emailSend) {
        EmailSend = emailSend;
    }

    public String getTextMess() {
        return TextMess;
    }

    public void setTextMess(String textMess) {
        TextMess = textMess;
    }

    public String getEmailReceived() {
        return EmailReceived;
    }

    public void setEmailReceived(String emailReceived) {
        EmailReceived = emailReceived;
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
}
