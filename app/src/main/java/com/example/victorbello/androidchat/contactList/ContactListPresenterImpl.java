package com.example.victorbello.androidchat.contactList;


/**
 * Created by ragnarok on 29/06/16.
 */

import org.greenrobot.eventbus.Subscribe;

import com.example.victorbello.androidchat.contactList.event.ContactListEvent;
import com.example.victorbello.androidchat.contactList.ui.ContactListView;
import com.example.victorbello.androidchat.entities.User;
import com.example.victorbello.androidchat.lib.EventBus;
import com.example.victorbello.androidchat.lib.GreenRobotEventBus;


public class ContactListPresenterImpl implements ContactListPresenter {

    private EventBus eventBus;
    private ContactListView view;
    private ContactListInteractor contactListInteractor;
    private ContactListSessionInteractor contactListSessionInteractor;


    public ContactListPresenterImpl(ContactListView view){
        this.view=view;
        this.eventBus= GreenRobotEventBus.getInstance();
        this.contactListInteractor=new ContactListInteractorImpl();
        this.contactListSessionInteractor=new ContactListSessionInteractorImpl();
    }

    @Override
    public void onPause() {
        contactListSessionInteractor.changeConnectionStatus(User.OFFLINE);
        contactListInteractor.unsubscribe();
    }

    @Override
    public void onResume() {
        contactListSessionInteractor.changeConnectionStatus(User.ONLINE);
        contactListInteractor.subscribe();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        contactListInteractor.destroyListener();
        view=null;
    }

    @Override
    public void signOff() {
        contactListSessionInteractor.changeConnectionStatus(User.OFFLINE);
        contactListInteractor.destroyListener();
        contactListInteractor.unsubscribe();
        contactListSessionInteractor.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return contactListSessionInteractor.getCurrentUserEmail();
    }

    @Override
    public void removeConctact(String email) {
        contactListInteractor.removeContact(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ContactListEvent event) {
        User user=event.getUser();
        switch (event.getEventType()){
            case ContactListEvent.onConctactAdded:
                onConctactAdded(user);
                break;
            case ContactListEvent.onConctactRemoved:
                onConctactRemoved(user);
                break;
            case ContactListEvent.onContactChanged:
                onContactChanged(user);
                break;
        }
    }

    private void onConctactAdded(User user){
        if(view!=null){
            view.onContactAdded(user);
        }
    }

    private void onConctactRemoved(User user){
        if(view!=null){
            view.onContactRemoved(user);
        }
    }

    private void onContactChanged(User user){
        if(view!=null){
            view.onContactChanged(user);
        }
    }
}
