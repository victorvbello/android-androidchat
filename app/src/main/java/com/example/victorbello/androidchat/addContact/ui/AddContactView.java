package com.example.victorbello.androidchat.addContact.ui;

/**
 * Created by ragnarok on 05/07/16.
 */
public interface AddContactView {
    void showInput();
    void hideInput();
    void showProgress();
    void hideProgress();

    void contactAdded();
    void contactNotAdded();

}
