package com.example.victorbello.androidchat.login;

/**
 * Created by ragnarok on 24/06/16.
 */


import android.support.annotation.NonNull;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

import com.example.victorbello.androidchat.domain.FirebaseHelper;
import com.example.victorbello.androidchat.lib.EventBus;
import com.example.victorbello.androidchat.lib.GreenRobotEventBus;
import com.example.victorbello.androidchat.login.events.LoginEvent;
import com.example.victorbello.androidchat.entities.User;

import android.util.Log;

public class LoginRepositoryImpl implements LoginRepository {

    private FirebaseHelper firebaseHelper;
    private DatabaseReference dataReference;
    private DatabaseReference myUserReference;

    public LoginRepositoryImpl(){
        firebaseHelper=FirebaseHelper.getInstance();
        dataReference=firebaseHelper.getDataReference();
        myUserReference=firebaseHelper.getMyUserReference();
    }

    @Override
    public void signUp(final String email, final String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>(){
                        @Override
                        public void onSuccess(AuthResult authResult){
                            postEvent(LoginEvent.onSignUpSuccess);
                            signIn(email,password);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener(){
                       @Override
                        public void onFailure(@NonNull Exception e){
                           postEvent(LoginEvent.onSignUpError,e.getMessage());
                       }
                    });
    }

    @Override
    public void signIn(String email, String password) {
        try{
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>(){
                        @Override
                        public void onSuccess(AuthResult authResult){
                            myUserReference=firebaseHelper.getMyUserReference();
                            myUserReference.addListenerForSingleValueEvent(new ValueEventListener(){
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot){
                                    initSignIn(dataSnapshot);
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError){
                                    postEvent(LoginEvent.onSignInError,databaseError.getMessage());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener(){
                        @Override
                        public void onFailure(@NonNull Exception e){
                            postEvent(LoginEvent.onSignInError,e.getMessage());
                        }
                    });
        }catch(Exception e){
            postEvent(LoginEvent.onSignInError,e.getMessage());
        }
    }

    @Override
    public void checkAlreadyAuthenticated() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            myUserReference=firebaseHelper.getMyUserReference();
            myUserReference.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot dataSnapshot){
                    if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
                        initSignIn(dataSnapshot);
                    }else{
                        postEvent(LoginEvent.onFailedToRecoverSession);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError){
                    postEvent(LoginEvent.onSignInError,databaseError.getMessage());
                }
            });
        }else{
            postEvent(LoginEvent.onFailedToRecoverSession);
        }
    }

    private void registerNewUser(){
        String email=firebaseHelper.getAuthUserEmail();
        if(email!=null){
            User currentUser=new User(email,true,null);
            myUserReference.setValue(currentUser);
        }
    }

    private void initSignIn(DataSnapshot dataSnapshot){
        User currentUser=dataSnapshot.getValue(User.class);
        if(currentUser==null){
            registerNewUser();
        }
        firebaseHelper.changeUserConnectionStatus(User.ONLINE);
        postEvent(LoginEvent.onSignInSuccess);
    }

    private void postEvent(int type, String errorMenssage){
        LoginEvent loginEvent=new LoginEvent();
        loginEvent.setEventType(type);
        if(errorMenssage!=null){
            loginEvent.setErrorMessage(errorMenssage);
        }

        EventBus eventBus= GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }

    private void postEvent(int type){
        this.postEvent(type,null);
    }
}
