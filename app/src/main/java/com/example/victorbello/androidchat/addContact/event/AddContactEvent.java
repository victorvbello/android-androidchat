package com.example.victorbello.androidchat.addContact.event;

/**
 * Created by ragnarok on 05/07/16.
 */
public class AddContactEvent {
    private boolean error;

    public AddContactEvent(){
        error=false;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
