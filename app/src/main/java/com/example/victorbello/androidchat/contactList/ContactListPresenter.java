package com.example.victorbello.androidchat.contactList;

import com.example.victorbello.androidchat.contactList.event.ContactListEvent;

/**
 * Created by ragnarok on 26/06/16.
 */
public interface ContactListPresenter {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void signOff();
    String getCurrentUserEmail();
    void removeConctact(String email);
    void onEventMainThread(ContactListEvent event);
}
