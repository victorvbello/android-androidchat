package com.example.victorbello.androidchat.addContact;


/**
 * Created by ragnarok on 06/07/16.
 */

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import com.example.victorbello.androidchat.addContact.event.AddContactEvent;
import com.example.victorbello.androidchat.domain.FirebaseHelper;
import com.example.victorbello.androidchat.lib.EventBus;
import com.example.victorbello.androidchat.lib.GreenRobotEventBus;
import com.example.victorbello.androidchat.entities.User;


public class AddContactRepositoryImpl implements AddContactRepository {

    private EventBus eventBus;
    private FirebaseHelper firebaseHelper;

    public AddContactRepositoryImpl(){
        this.eventBus= GreenRobotEventBus.getInstance();
        this.firebaseHelper=FirebaseHelper.getInstance();
    }

    @Override
    public void addContact(final String email) {
        final String key=email.replace(".","_");
        DatabaseReference userReference=firebaseHelper.getUserReference(email);
        userReference.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                User user=dataSnapshot.getValue(User.class);
                if(user!=null){
                    DatabaseReference myContactReference=firebaseHelper.getMyContactsReference();
                    myContactReference.child(key).setValue(user.isOnline());

                    String currentUserKey=firebaseHelper.getAuthUserEmail();
                    currentUserKey=currentUserKey.replace(".","_");
                    DatabaseReference reverseContactReference=firebaseHelper.getContactsReference(email);
                    reverseContactReference.child(currentUserKey).setValue(User.ONLINE);

                    postSuccess();
                }else{
                    postError();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError){
                postError();
            }
        });
    }

    private void postSuccess(){
        post(false);
    }

    private void postError(){
        post(true);
    }

    private void post(boolean error){
        AddContactEvent event=new AddContactEvent();
        event.setError(error);
        eventBus.post(event);
    }
}
