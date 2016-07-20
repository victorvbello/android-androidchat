package com.example.victorbello.androidchat.chat.event;


/**
 * Created by ragnarok on 11/07/16.
 */

import com.example.victorbello.androidchat.entities.ChatMessage;

public class ChatEvent {

    ChatMessage message;

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }
}
