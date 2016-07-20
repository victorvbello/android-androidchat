package com.example.victorbello.androidchat.chat;

/**
 * Created by victorbello on 18/07/16.
 */
public class ChatSessionInteractorImpl implements ChatSessionInteractor {

    ChatRepository repository;

    public ChatSessionInteractorImpl(){
        repository=new ChatRepositoryImpl();
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        repository.changeConnectionStatus(online);
    }
}
