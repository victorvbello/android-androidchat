package com.example.victorbello.androidchat.login;

/**
 * Created by ragnarok on 24/06/16.
 */
public interface LoginRepository {
    void signUp(String email, String password);
    void signIn(String email,String password);
    void checkAlreadyAuthenticated();
}
