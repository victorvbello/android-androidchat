package com.example.victorbello.androidchat.chat;


/**
 * Created by victorbello on 15/07/16.
 */

import com.example.victorbello.androidchat.chat.event.ChatEvent;
import com.example.victorbello.androidchat.chat.ui.ChatView;
import com.example.victorbello.androidchat.entities.User;
import com.example.victorbello.androidchat.lib.EventBus;
import com.example.victorbello.androidchat.lib.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

public class ChatPresenterImpl implements ChatPresenter {

    private EventBus eventBus;
    private ChatView view;
    private ChatInteractor chatInteractor;
    private ChatSessionInteractor sessionInteractor;

    public ChatPresenterImpl(ChatView view){
        this.view=view;
        this.eventBus=GreenRobotEventBus.getInstance();
        this.chatInteractor=new ChatInteractorImpl();
        this.sessionInteractor=new ChatSessionInteractorImpl();
    }

    @Override
    public void onPause() {
        chatInteractor.unsubscribe();
        sessionInteractor.changeConnectionStatus(User.OFFLINE);
    }

    @Override
    public void onResume() {
        chatInteractor.subscribe();
        sessionInteractor.changeConnectionStatus(User.ONLINE);
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        chatInteractor.destroyListener();
        view=null;
    }

    @Override
    public void setChatRecipient(String recipient) {
        chatInteractor.setRecipient(recipient);
    }

    @Override
    public void sendMessage(String msg) {
        chatInteractor.sendMessage(msg);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ChatEvent event) {
        if(view!=null){
            view.onMessageReceived(event.getMessage());
        }
    }
}
