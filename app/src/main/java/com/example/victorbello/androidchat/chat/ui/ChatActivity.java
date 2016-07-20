package com.example.victorbello.androidchat.chat.ui;

/**
 * Created by ragnarok on 09/07/16.
 */

import android.os.Bundle;

import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.content.Intent;
import android.view.View;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import de.hdodenhof.circleimageview.CircleImageView;

import com.example.victorbello.androidchat.R;
import com.example.victorbello.androidchat.chat.ChatPresenter;
import com.example.victorbello.androidchat.chat.ChatPresenterImpl;
import com.example.victorbello.androidchat.entities.ChatMessage;
import com.example.victorbello.androidchat.chat.ui.adapter.ChatAdapter;
import com.example.victorbello.androidchat.domain.AvatarHelper;
import com.example.victorbello.androidchat.lib.GlideImageLoader;
import com.example.victorbello.androidchat.lib.ImageLoader;

public class ChatActivity extends AppCompatActivity implements ChatView{

    private CircleImageView imgAvatar;
    private TextView txtUser;
    private TextView txtStatus;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private EditText inputMessage;
    private ImageButton btnSendMessage;

    private ChatPresenter presenter;
    private ChatAdapter adapter;

    public final static String EMAIL_KEY="email";
    public final static String ONLINE_KEY="onlline";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        imgAvatar=(CircleImageView) findViewById(R.id.imgAvatar);
        txtUser=(TextView) findViewById(R.id.txtUser);
        txtStatus=(TextView) findViewById(R.id.txtStatus);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        recyclerView=(RecyclerView) findViewById(R.id.messageRecyclerView);
        inputMessage=(EditText) findViewById(R.id.inputMessage);
        btnSendMessage=(ImageButton) findViewById(R.id.btnSendMessage);
        btnSendMessage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                sendMessage();
            }
        });

        setupAdapter();
        setupRecyclerView();

        presenter=new ChatPresenterImpl(this);
        presenter.onCreate();
        setupToolbar(getIntent());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setupAdapter(){
        /*
        ChatMessage msg1=new ChatMessage();
        ChatMessage msg2=new ChatMessage();

        msg1.setMsg("Hola!");
        msg1.setSentByMe(true);

        msg2.setMsg("Como esta?");
        msg2.setSentByMe(false);

        adapter=new ChatAdapter(this,Arrays.asList(new ChatMessage[]{msg1,msg2}));
        */
        adapter=new ChatAdapter(this,new ArrayList<ChatMessage>());
    }

    private void setupRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupToolbar(Intent i){
        String recipient=i.getStringExtra(EMAIL_KEY);
        presenter.setChatRecipient(recipient);

        boolean online=i.getBooleanExtra(ONLINE_KEY,false);
        String status=online?getString(R.string.chat_message_online): getString(R.string.chat_message_offline);
        int color=online? Color.GREEN:Color.RED;

        txtUser.setText(recipient);
        txtStatus.setText(status);
        txtStatus.setTextColor(color);

        ImageLoader imageLoader =new GlideImageLoader(getApplicationContext());
        imageLoader.load(imgAvatar, AvatarHelper.getAvatarUrl(recipient));

        setSupportActionBar(toolbar);
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onMessageReceived(ChatMessage msg) {
        adapter.add(msg);
        recyclerView.scrollToPosition(adapter.getItemCount()-1);
    }

    public void sendMessage(){
        presenter.sendMessage(inputMessage.getText().toString());
        inputMessage.setText("");
    }

}
