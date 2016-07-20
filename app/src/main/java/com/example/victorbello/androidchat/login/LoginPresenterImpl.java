package com.example.victorbello.androidchat.login;


/**
 * Created by ragnarok on 24/06/16.
 */

import org.greenrobot.eventbus.Subscribe;

import com.example.victorbello.androidchat.login.events.LoginEvent;
import com.example.victorbello.androidchat.lib.EventBus;
import com.example.victorbello.androidchat.lib.GreenRobotEventBus;
import com.example.victorbello.androidchat.login.ui.LoginView;

public class LoginPresenterImpl implements LoginPresenter {
    private EventBus eventBus;
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView){
        this.loginView=loginView;
        this.loginInteractor=new LoginInteractorImpl();
        this.eventBus= GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        this.loginView=null;
        eventBus.unregister(this);
    }

    @Override
    public void checkForAuthenticateUser() {
        if(this.loginView!=null){
            loginView.disableInput();
            loginView.showProgress();
        }
        loginInteractor.checkSession();
    }

    @Override
    public void validateLogin(String email, String password) {
        if(this.loginView!=null){
            loginView.disableInput();
            loginView.showProgress();
        }
        loginInteractor.doSignIn(email,password);
    }

    @Override
    public void registerNewUser(String email, String password) {
        if(this.loginView!=null){
            loginView.disableInput();
            loginView.showProgress();
        }

        loginInteractor.doSignUp(email,password);
    }

    @Subscribe
    @Override
    public void onEventMainThread(LoginEvent event) {
        switch (event.getEventType()){
            case LoginEvent.onSignInSuccess:
                onSignInSuccess();
                break;
            case LoginEvent.onSignInError:
                onSignInError(event.getErrorMessage());
                break;
            case LoginEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
            case LoginEvent.onSignUpError:
                onSignUpError(event.getErrorMessage());
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedToRecoverSession();
                break;
        }
    }

    private void onSignInSuccess(){
        if(this.loginView!=null){
            loginView.navigateToMainScreen();
        }
    }

    private void onSignUpSuccess(){
        if(this.loginView!=null){
            loginView.newUserSuccess();
        }
    }

    private void onSignInError(String error){
        if(this.loginView!=null){
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.loginError(error);
        }
    }

    private void onSignUpError(String error){
        if(this.loginView!=null){
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.newUserError(error);
        }
    }

    private void onFailedToRecoverSession(){
        if(this.loginView!=null){
            loginView.hideProgress();
            loginView.enableInputs();
        }
    }

}
