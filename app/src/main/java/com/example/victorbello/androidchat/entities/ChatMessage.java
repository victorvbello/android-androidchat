package com.example.victorbello.androidchat.entities;


/**
 * Created by ragnarok on 11/07/16.
 */

import com.google.firebase.database.Exclude;

public class ChatMessage {

    private String msg;
    private String  sender;
    @Exclude
    private boolean sentByMe;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isSentByMe() {
        return sentByMe;
    }

    public void setSentByMe(boolean sentByMe) {
        this.sentByMe = sentByMe;
    }

    @Override
    public boolean equals(Object obj){
        boolean equal=false;
        if(obj instanceof ChatMessage){
            ChatMessage msg=(ChatMessage) obj;
            equal=this.msg.equals(msg.getMsg()) && this.sender.equals(msg.getSender())&& this.sentByMe==msg.sentByMe;
        }
        return equal;
    }

}
