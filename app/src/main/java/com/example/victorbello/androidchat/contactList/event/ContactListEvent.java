package com.example.victorbello.androidchat.contactList.event;

/**
 * Created by ragnarok on 26/06/16.
 */

import com.example.victorbello.androidchat.entities.User;

public class ContactListEvent {
    public final static int onConctactAdded=0;
    public final static int onContactChanged=1;
    public final static int onConctactRemoved=2;

    private User user;
    private int eventType;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}
