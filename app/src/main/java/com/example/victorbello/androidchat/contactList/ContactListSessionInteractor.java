package com.example.victorbello.androidchat.contactList;

/**
 * Created by ragnarok on 26/06/16.
 */
public interface ContactListSessionInteractor {
    void signOff();
    String getCurrentUserEmail();
    void changeConnectionStatus(boolean online);
}
