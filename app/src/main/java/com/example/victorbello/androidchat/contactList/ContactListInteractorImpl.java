package com.example.victorbello.androidchat.contactList;

/**
 * Created by ragnarok on 29/06/16.
 */
public class ContactListInteractorImpl implements ContactListInteractor {

    private ContactListRepository contactListRepository;

    public ContactListInteractorImpl(){
        contactListRepository=new ContactListRepositoryImpl();
    }

    @Override
    public void subscribe() {
        contactListRepository.subscribeToContactListEvents();
    }

    @Override
    public void unsubscribe() {
        contactListRepository.unsubscribeToContactListEvents();
    }

    @Override
    public void destroyListener() {
        contactListRepository.destroyListener();
    }

    @Override
    public void removeContact(String email) {
        contactListRepository.removeContact(email);
    }
}
