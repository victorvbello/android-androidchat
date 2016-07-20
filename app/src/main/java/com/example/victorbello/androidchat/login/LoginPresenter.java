package com.example.victorbello.androidchat.login;


/**
 * Created by ragnarok on 24/06/16.
 */

import com.example.victorbello.androidchat.login.events.LoginEvent;

public interface LoginPresenter {
    void onCreate();
    void onDestroy();
    void checkForAuthenticateUser();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);
    void onEventMainThread(LoginEvent event);
}
