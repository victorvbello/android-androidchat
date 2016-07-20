package com.example.victorbello.androidchat.addContact;

/**
 * Created by ragnarok on 06/07/16.
 */
public class AddContactInteractorImpl implements AddContactInteractor {

    private AddContactRepository addContactRepository;

    public AddContactInteractorImpl(){
        this.addContactRepository=new AddContactRepositoryImpl();
    }

    @Override
    public void execute(String email) {
        addContactRepository.addContact(email);
    }
}
