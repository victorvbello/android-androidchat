package com.example.victorbello.androidchat.chat;

/**
 * Created by ragnarok on 11/07/16.
 */

import com.example.victorbello.androidchat.chat.event.ChatEvent;

public interface ChatPresenter {

    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void setChatRecipient(String recipient);
    void sendMessage(String msg);
    void onEventMainThread(ChatEvent event);
}
