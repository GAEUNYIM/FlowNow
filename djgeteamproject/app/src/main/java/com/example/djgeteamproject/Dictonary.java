package com.example.djgeteamproject;

class Dictonary{
    private String username;
    private String phonenumber;
    private int id;

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

    public void setId(int id){ this.id = id;}

    public int getId(int id){ return id; }

    public Dictonary(String username, String phonenumber) {
        this.username = username;
        this.phonenumber = phonenumber;
    }
}
