package com.example.victorbello.androidchat.addContact.ui;

/**
 * Created by ragnarok on 05/07/16.
 */

import android.support.v4.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Button;

import com.example.victorbello.androidchat.R;
import com.example.victorbello.androidchat.addContact.AddContactPresenter;
import com.example.victorbello.androidchat.addContact.AddContactPresenterImpl;

public class AddContactFragment extends DialogFragment implements AddContactView, DialogInterface.OnShowListener{

    private EditText inputEmail;
    private ProgressBar progressBar;
    private AddContactPresenter addContactPresenter;

    public AddContactFragment() {
        addContactPresenter = new AddContactPresenterImpl(this);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.addContact_message_title))
                .setPositiveButton(R.string.addContact_message_add, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setNegativeButton(R.string.addContact_message_cancel,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){}
                });
        View view=getActivity().getLayoutInflater().inflate(R.layout.fragment_add_contact,null);
        inputEmail=(EditText) view.findViewById(R.id.inputEmail);
        progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.setOnShowListener(this);
        return dialog;
    }

    @Override
    public void showInput() {
        inputEmail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideInput() {
        inputEmail.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void contactAdded() {
        Toast.makeText(getActivity(),R.string.addContact_message_contactadded,Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void contactNotAdded() {
        inputEmail.setText("");
        inputEmail.setError(getString(R.string.addContact_message_error));
    }

    @Override
    public void onShow(DialogInterface dialogInterface){
        final AlertDialog dialog=(AlertDialog) getDialog();
        if(dialog!=null){
            Button positiveButton=dialog.getButton(Dialog.BUTTON_POSITIVE);
            Button negativeButton=dialog.getButton(Dialog.BUTTON_NEGATIVE);

            positiveButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    addContactPresenter.addContact(inputEmail.getText().toString());
                }
            });

            negativeButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    dismiss();
                }
            });
        }
        addContactPresenter.onShow();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        addContactPresenter.onDestroy();
    }

}
