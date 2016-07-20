package com.example.victorbello.androidchat.contactList.ui;

/**
 * Created by ragnarok on 26/06/16.
 */

import com.example.victorbello.androidchat.entities.User;

public interface ContactListView {
    void onContactAdded(User user);
    void onContactChanged(User user);
    void onContactRemoved(User user);
}
