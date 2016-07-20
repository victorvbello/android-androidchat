package com.example.victorbello.androidchat.login;

/**
 * Created by ragnarok on 24/06/16.
 */
public class LoginInteractorImpl implements LoginInteractor {
    private LoginRepository loginRepository;


    public LoginInteractorImpl(){
        loginRepository=new LoginRepositoryImpl();
    }

    @Override
    public void checkSession() {
        loginRepository.checkAlreadyAuthenticated();
    }

    @Override
    public void doSignUp(String email, String password) {
        loginRepository.signUp(email,password);
    }

    @Override
    public void doSignIn(String email, String password) {
        loginRepository.signIn(email,password);
    }
}
