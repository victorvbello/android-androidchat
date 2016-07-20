package com.example.victorbello.androidchat.login.ui;

/**
 * Created by ragnarok on 24/06/16.
 */
public interface LoginView {
    void enableInputs();
    void disableInput();
    void showProgress();
    void hideProgress();

    void handleSignUp();
    void handleSignIn();

    void navigateToMainScreen();
    void loginError(String error);

    void newUserSuccess();
    void newUserError(String error);
}
