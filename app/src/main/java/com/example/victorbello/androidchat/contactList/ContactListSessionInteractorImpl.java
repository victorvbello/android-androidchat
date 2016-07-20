package com.example.victorbello.androidchat.contactList;

/**
 * Created by ragnarok on 29/06/16.
 */
public class ContactListSessionInteractorImpl implements ContactListSessionInteractor {

    private ContactListRepository contactListRepository;

    public ContactListSessionInteractorImpl(){
        contactListRepository=new ContactListRepositoryImpl();
    }


    @Override
    public void signOff() {
        contactListRepository.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return contactListRepository.getCurrentUserEmail();
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        contactListRepository.changeConnectioStatus(online);
    }
}
