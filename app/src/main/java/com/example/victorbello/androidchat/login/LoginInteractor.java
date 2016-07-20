package com.example.victorbello.androidchat.login;

/**
 * Created by ragnarok on 24/06/16.
 */
public interface LoginInteractor {
    void checkSession();
    void doSignUp(String email, String password);
    void doSignIn(String email, String password);
}
