package com.example.djgeteamproject;

class Dictonary{
    private String username;
    private String phonenumber;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Dictonary(String username, String phonenumber) {
        this.username = username;
        this.phonenumber = phonenumber;
    }
}
