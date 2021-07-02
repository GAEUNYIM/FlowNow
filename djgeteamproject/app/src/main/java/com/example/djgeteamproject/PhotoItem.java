package com.example.djgeteamproject;

import android.net.Uri;

public class PhotoItem {
    private Uri data;
    private String displayName;
    private String path;
    private int id;

    PhotoItem(){}

    public Uri getData() {
        return data;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getId(int id){ return id; }

    public String getpath() {return path;}

    public void setData(Uri username) {
        this.data = data;
    }

    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public void setId(int id){ this.id = id;}

    public void setpath(String dpath) {this.path = dpath;}
    public PhotoItem(Uri data, String displayName, String path) {
        this.data = data;
        this.displayName = displayName;
        this.path = path;
    }
}
