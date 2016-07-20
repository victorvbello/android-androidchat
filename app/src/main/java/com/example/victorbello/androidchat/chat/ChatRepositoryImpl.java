package com.example.victorbello.androidchat.chat;

/**
 * Created by victorbello on 18/07/16.
 */

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.example.victorbello.androidchat.domain.FirebaseHelper;
import com.example.victorbello.androidchat.entities.ChatMessage;
import com.example.victorbello.androidchat.lib.EventBus;
import com.example.victorbello.androidchat.lib.GreenRobotEventBus;
import com.example.victorbello.androidchat.chat.event.ChatEvent;

public class ChatRepositoryImpl implements ChatRepository {

    private String recipient;
    private FirebaseHelper helper;
    private ChildEventListener chatEventListener;

    public ChatRepositoryImpl(){
        this.helper=FirebaseHelper.getInstance();
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        helper.changeUserConnectionStatus(online);
    }

    @Override
    public void sendMessage(String msg) {
        String keySender=helper.getAuthUserEmail().replace(".","_");
        ChatMessage chatMessage=new ChatMessage();
        chatMessage.setSender(keySender);
        chatMessage.setMsg(msg);

        DatabaseReference databaseReference=helper.getChatsReference(recipient);
        databaseReference.push().setValue(chatMessage);
    }

    @Override
    public void setRecipient(String recipient) {
        this.recipient=recipient;
    }

    @Override
    public void subscribe() {
        if(chatEventListener==null){
            chatEventListener=new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatMessage chatMessage=dataSnapshot.getValue(ChatMessage.class);
                    String msgSender=chatMessage.getSender().replace(".","_");

                    Log.e("ChatAdapter",msgSender);

                    chatMessage.setSentByMe(msgSender.equals(helper.getAuthUserEmail().replace(".","_")));



                    ChatEvent chatEvent=new ChatEvent();
                    chatEvent.setMessage(chatMessage);
                    EventBus eventBus=GreenRobotEventBus.getInstance();
                    eventBus.post(chatEvent);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
        }
        helper.getChatsReference(recipient).addChildEventListener(chatEventListener);
    }

    @Override
    public void unsubscribe() {
        if(chatEventListener!=null){
            helper.getChatsReference(recipient).removeEventListener(chatEventListener);
        }
    }

    @Override
    public void destroyListener() {
        chatEventListener=null;
    }
}
