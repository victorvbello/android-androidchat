package com.example.victorbello.androidchat.chat.ui;

/**
 * Created by ragnarok on 11/07/16.
 */

import com.example.victorbello.androidchat.entities.ChatMessage;

public interface ChatView {
    void onMessageReceived(ChatMessage msg);
}
