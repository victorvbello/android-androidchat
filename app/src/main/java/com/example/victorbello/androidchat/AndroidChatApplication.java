package com.example.victorbello.androidchat;

/**
 * Created by ragnarok on 22/06/16.
 */

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;


public class AndroidChatApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        setupFirebase();
    }

    private void setupFirebase(){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
