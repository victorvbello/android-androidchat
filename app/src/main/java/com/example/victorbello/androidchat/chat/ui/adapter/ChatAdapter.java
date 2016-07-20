package com.example.victorbello.androidchat.chat.ui.adapter;

/**
 * Created by victorbello on 15/07/16.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.LinearLayout;

import java.util.List;
import android.util.TypedValue;
import android.util.Log;

import android.support.v7.widget.RecyclerView;

import com.example.victorbello.androidchat.R;
import com.example.victorbello.androidchat.entities.ChatMessage;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context context;
    private List<ChatMessage> chatMessages;

    public ChatAdapter(Context context, List<ChatMessage> chatMessages){
        this.context=context;
        this.chatMessages=chatMessages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.content_chat,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage chatMessage=chatMessages.get(position);

        String msg=chatMessage.getMsg();
        holder.txtMessage.setText(msg);

        int color=fetchColor(R.attr.colorPrimary);
        int gravity=Gravity.LEFT;

        if(!chatMessage.isSentByMe()){
            color=fetchColor(R.attr.colorAccent);
            gravity=Gravity.RIGHT;
        }

        holder.txtMessage.setBackgroundColor(color);
        LinearLayout.LayoutParams params =(LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
        params.gravity=gravity;
        holder.txtMessage.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public void add(ChatMessage msg){
        /*
        if(chatMessages.contains(msg)){
        }
        */
        chatMessages.add(msg);
        notifyDataSetChanged();
    }

    public int fetchColor(int color){
        TypedValue typedValue=new TypedValue();
        TypedArray att=context.obtainStyledAttributes(typedValue.data,new int[]{color});
        int returnColor=att.getColor(0,0);
        att.recycle();
        return returnColor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            txtMessage=(TextView)itemView.findViewById(R.id.txtMessage);
        }
    }
}
