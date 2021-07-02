package com.example.djgeteamproject;

public class PhotoItem {

    private String data, displayName;
    private int id;

    PhotoItem(){}

    public String getData() {
        return data;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getId(int id){ return id; }


    public void setData(String username) {
        this.data = data;
    }

    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public void setId(int id){ this.id = id;}

    public PhotoItem(String data, String displayName) {
        this.data = data;
        this.displayName = displayName;
    }
}
