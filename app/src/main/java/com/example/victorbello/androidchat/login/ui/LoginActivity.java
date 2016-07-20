package com.example.victorbello.androidchat.login.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

import android.util.Log;

import com.example.victorbello.androidchat.R;
import com.example.victorbello.androidchat.contactList.ui.ContactListActivity;
import com.example.victorbello.androidchat.login.LoginPresenter;
import com.example.victorbello.androidchat.login.LoginPresenterImpl;

public class LoginActivity extends AppCompatActivity implements OnClickListener,LoginView {

    private EditText inputEmail;
    private EditText inputPassword;
    private Button btnSignin;
    private Button btnSignup;
    private ProgressBar progressBar;
    private RelativeLayout layoutMainContainer;

    private LoginPresenter loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail=(EditText) findViewById(R.id.inputEmail);
        inputPassword=(EditText) findViewById(R.id.inputPassword);

        btnSignin=(Button) findViewById(R.id.btnSignin);
        btnSignup=(Button) findViewById(R.id.btnSignup);

        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        layoutMainContainer=(RelativeLayout) findViewById(R.id.layoutMainContainer);

        btnSignin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);

        loginPresenter=new LoginPresenterImpl(this);
        loginPresenter.onCreate();
        loginPresenter.checkForAuthenticateUser();
    }

    //Metodos botones

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btnSignin:
                handleSignIn();
                break;
            case R.id.btnSignup:
                handleSignUp();
                break;
        }
    }

    @Override
    public void onDestroy(){
        loginPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void enableInputs() {
        this.setInputs(true);
    }

    @Override
    public void disableInput() {
        this.setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void handleSignUp() {
        loginPresenter.registerNewUser(inputEmail.getText().toString(),
                                        inputPassword.getText().toString());
    }

    @Override
    public void handleSignIn() {
        loginPresenter.validateLogin(inputEmail.getText().toString(),
                                    inputPassword.getText().toString());
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, ContactListActivity.class));
    }

    @Override
    public void loginError(String error) {
        inputPassword.setText("");
        String msgError=String.format(getString(R.string.login_error_message_signin),error);
        inputPassword.setError(msgError);
    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(layoutMainContainer,R.string.login_notice_message_signup,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String error) {
        inputPassword.setText("");
        String msgError=String.format(getString(R.string.login_error_message_signup),error);
        inputPassword.setError(msgError);
    }

    private void setInputs(boolean enabled){
        inputEmail.setEnabled(enabled);
        inputPassword.setEnabled(enabled);
        btnSignin.setEnabled(enabled);
        btnSignup.setEnabled(enabled);
    }
}
