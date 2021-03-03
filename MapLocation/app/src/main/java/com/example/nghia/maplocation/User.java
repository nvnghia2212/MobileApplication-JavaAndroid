package com.example.nghia.maplocation;

public class User {
    private int Id;
    private String Email;
    private String GivenName;
    private String Name;
    private Double Latitude;
    private Double Longitude;
    private int OnlOff;

    public User(int id, String email, String givenName, String name, Double latitude, Double longitude, int onlOff) {
        Id = id;
        Email = email;
        GivenName = givenName;
        Name = name;
        Latitude = latitude;
        Longitude = longitude;
        OnlOff = onlOff;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGivenName() {
        return GivenName;
    }

    public void setGivenName(String givenName) {
        GivenName = givenName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public int getOnlOff() {
        return OnlOff;
    }

    public void setOnlOff(int onlOff) {
        OnlOff = onlOff;
    }
}
