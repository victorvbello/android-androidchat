package com.example.victorbello.androidchat.contactList;

/**
 * Created by ragnarok on 29/06/16.
 */

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.example.victorbello.androidchat.contactList.event.ContactListEvent;
import com.example.victorbello.androidchat.domain.FirebaseHelper;
import com.example.victorbello.androidchat.lib.EventBus;
import com.example.victorbello.androidchat.entities.User;
import com.example.victorbello.androidchat.lib.GreenRobotEventBus;

public class ContactListRepositoryImpl implements ContactListRepository {

    private FirebaseHelper firebaseHelper;
    private ChildEventListener childEventListener;
    private EventBus eventBus;

    public ContactListRepositoryImpl(){
        firebaseHelper=FirebaseHelper.getInstance();
        eventBus= GreenRobotEventBus.getInstance();
    }

    @Override
    public void signOff() {
        firebaseHelper.singOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return firebaseHelper.getAuthUserEmail();
    }

    @Override
    public void removeContact(String email) {
        String currentUserEmail=firebaseHelper.getAuthUserEmail();
        firebaseHelper.getOneContactReference(currentUserEmail,email).removeValue();
        firebaseHelper.getOneContactReference(email,currentUserEmail).removeValue();
    }

    @Override
    public void destroyListener() {
        childEventListener=null;
    }

    @Override
    public void subscribeToContactListEvents() {
        if(childEventListener==null){
            childEventListener=new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot,ContactListEvent.onConctactAdded);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot,ContactListEvent.onContactChanged);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    handleContact(dataSnapshot,ContactListEvent.onConctactRemoved);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };
        }
        firebaseHelper.getMyContactsReference().addChildEventListener(childEventListener);
    }

    private void post(int eventType,User user){
        ContactListEvent event =new ContactListEvent();
        event.setEventType(eventType);
        event.setUser(user);
        eventBus.post(event);
    }

    private void handleContact(DataSnapshot dataSnapshot,int type){
        String email=dataSnapshot.getKey();
        email=email.replace("_",".");
        boolean online=((Boolean) dataSnapshot.getValue()).booleanValue();
        User user=new User();
        user.setEmail(email);
        user.setOnline(online);
        post(type,user);
    }


    @Override
    public void unsubscribeToContactListEvents() {
        if(childEventListener!=null){
            firebaseHelper.getMyContactsReference().removeEventListener(childEventListener);
        }
    }

    @Override
    public void changeConnectioStatus(boolean online) {
        firebaseHelper.changeUserConnectionStatus(online);
    }
}
