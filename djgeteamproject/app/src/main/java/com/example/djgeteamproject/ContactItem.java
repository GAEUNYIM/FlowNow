package com.example.djgeteamproject;

import android.net.Uri;
import android.provider.ContactsContract;

public class ContactItem {

    private String username, phonenumber;
    private long person_id=0;
    private long photo_id;
    private int id;

    ContactItem(){}
    public String getUsername() {
        return username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public long getPhoto_id() { return photo_id; }

    public long getPerson_id() { return person_id; }

    public int getId(int id){ return id; }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setPhoto_id(long id){ this.photo_id = id; }

    public void setPerson_id(long id) { this.person_id = id; }

    public void setId(int id){ this.id = id;}


    @Override
    public String toString(){
        return this.phonenumber;
    }

    @Override
    public int hashCode(){
        return getPhonenumberChanged().hashCode();
    }

    public String getPhonenumberChanged(){
        return phonenumber.replace("-", "");
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof ContactItem)
            return getPhonenumberChanged().equals(((ContactItem) o).getPhonenumberChanged());
        return false;
    }

    public ContactItem(String username, String phonenumber) {
        this.username = username;
        this.phonenumber = phonenumber;
    }
}
