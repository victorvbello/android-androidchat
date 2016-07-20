package com.example.victorbello.androidchat.addContact;

/**
 * Created by ragnarok on 05/07/16.
 */

import com.example.victorbello.androidchat.addContact.event.AddContactEvent;

public interface AddContactPresenter {
    void onShow();
    void onDestroy();

    void addContact(String email);
    void onEventMainThread(AddContactEvent event);
}
