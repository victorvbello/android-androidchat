package com.example.victorbello.androidchat.addContact;

/**
 * Created by ragnarok on 05/07/16.
 */

import org.greenrobot.eventbus.Subscribe;

import com.example.victorbello.androidchat.addContact.event.AddContactEvent;
import com.example.victorbello.androidchat.addContact.ui.AddContactView;
import com.example.victorbello.androidchat.lib.EventBus;
import com.example.victorbello.androidchat.lib.GreenRobotEventBus;


public class AddContactPresenterImpl implements AddContactPresenter {

    private AddContactView view;
    private EventBus eventBus;
    private AddContactInteractor addContactInteractor;

    public AddContactPresenterImpl(AddContactView view){
        this.view=view;
        this.eventBus= GreenRobotEventBus.getInstance();
        this.addContactInteractor=new AddContactInteractorImpl();
    }

    @Override
    public void onShow() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view=null;
        eventBus.unregister(this);
    }

    @Override
    public void addContact(String email) {
        if(view!=null){
            view.hideInput();
            view.showInput();
        }
        addContactInteractor.execute(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AddContactEvent event) {
        if(view!=null){
            view.hideProgress();
            view.showInput();

            if(event.isError()){
                view.contactNotAdded();
            }else{
                view.contactAdded();
            }
        }
    }
}
