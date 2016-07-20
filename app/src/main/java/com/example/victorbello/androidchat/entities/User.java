package com.example.victorbello.androidchat.entities;

/**
 * Created by ragnarok on 25/06/16.
 */

import java.util.Map;

public class User {
    String email;
    boolean online;
    Map<String,Boolean> conctacts;
    public final static boolean ONLINE=true;
    public final static boolean OFFLINE=false;

    public User(){}

    public User(String email,boolean online,Map<String,Boolean> contacts){
        this.email=email;
        this.online=online;
        this.conctacts=contacts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Map<String, Boolean> getConctacts() {
        return conctacts;
    }

    public void setConctacts(Map<String, Boolean> conctacts) {
        this.conctacts = conctacts;
    }

    @Override
    public boolean equals(Object obj){
        boolean equal=false;

        if (obj instanceof User){
            User user=(User)obj;
            equal =this.email.equals(user.getEmail());
        }

        return equal;
    }
}
