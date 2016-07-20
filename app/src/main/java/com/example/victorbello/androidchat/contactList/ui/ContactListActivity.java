package com.example.victorbello.androidchat.contactList.ui;

/**
 * Created by ragnarok on 24/06/16.
 */

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;

import com.example.victorbello.androidchat.R;
import com.example.victorbello.androidchat.addContact.ui.AddContactFragment;
import com.example.victorbello.androidchat.chat.ui.ChatActivity;
import com.example.victorbello.androidchat.contactList.ContactListPresenter;
import com.example.victorbello.androidchat.contactList.ContactListPresenterImpl;
import com.example.victorbello.androidchat.contactList.ui.adapters.ContactListAdapter;
import com.example.victorbello.androidchat.contactList.ui.adapters.OnItemClickListener;
import com.example.victorbello.androidchat.entities.User;
import com.example.victorbello.androidchat.lib.GlideImageLoader;
import com.example.victorbello.androidchat.lib.ImageLoader;
import com.example.victorbello.androidchat.login.ui.LoginActivity;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity implements OnClickListener,ContactListView,OnItemClickListener {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;

    private ContactListAdapter contactListAdapter;
    private ContactListPresenter contactListPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerViewContacts);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        floatingActionButton=(FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);

        setupAdapter();
        setupRecyclerView();
        contactListPresenter=new ContactListPresenterImpl(this);
        contactListPresenter.onCreate();
        setupToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_contactlist,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.action_logout){
            contactListPresenter.signOff();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupAdapter() {
        ImageLoader loader =new GlideImageLoader(this.getApplicationContext());
        contactListAdapter=new ContactListAdapter(new ArrayList<User>(),loader,this,this.getApplicationContext());
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contactListAdapter);
    }

    private void setupToolbar() {
        toolbar.setTitle(contactListPresenter.getCurrentUserEmail());
        setSupportActionBar(toolbar);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        contactListPresenter.onDestroy();
    }

    @Override
    public void onResume(){
        super.onResume();
        contactListPresenter.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        contactListPresenter.onPause();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.fab:
                addContact();
                break;
        }
    }

    public void addContact(){
        new AddContactFragment().show(getSupportFragmentManager(),getString(R.string.addContact_message_title));
    }

    @Override
    public void onContactAdded(User user) {
        contactListAdapter.add(user);
    }

    @Override
    public void onContactChanged(User user) {
        contactListAdapter.change(user);
    }

    @Override
    public void onContactRemoved(User user) {
        contactListAdapter.remove(user);
    }

    @Override
    public void onItemClick(User user) {
        Intent intent=new Intent(this,ChatActivity.class);
        intent.putExtra(ChatActivity.EMAIL_KEY,user.getEmail());
        intent.putExtra(ChatActivity.ONLINE_KEY,user.isOnline());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(User user) {
        contactListPresenter.removeConctact(user.getEmail());
    }
}
