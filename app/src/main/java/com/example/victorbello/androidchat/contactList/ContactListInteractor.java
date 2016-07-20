package com.example.victorbello.androidchat.contactList;

/**
 * Created by ragnarok on 26/06/16.
 */
public interface ContactListInteractor {
    void subscribe();
    void unsubscribe();
    void destroyListener();
    void removeContact(String email);
}
