package com.example.victorbello.androidchat.chat;

/**
 * Created by ragnarok on 11/07/16.
 */
public interface ChatRepository {
    void changeConnectionStatus(boolean online);

    void sendMessage(String msg);
    void setRecipient(String recipient);

    void subscribe();
    void unsubscribe();
    void destroyListener();
}
