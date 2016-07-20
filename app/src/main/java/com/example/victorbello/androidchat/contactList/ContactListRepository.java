package com.example.victorbello.androidchat.contactList;

/**
 * Created by ragnarok on 26/06/16.
 */
public interface ContactListRepository {
    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);
    void destroyListener();
    void subscribeToContactListEvents();
    void unsubscribeToContactListEvents();
    void changeConnectioStatus(boolean online);
}
