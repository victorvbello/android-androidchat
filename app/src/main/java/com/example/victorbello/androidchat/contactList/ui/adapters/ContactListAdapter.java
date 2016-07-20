package com.example.victorbello.androidchat.contactList.ui.adapters;



/**
 * Created by ragnarok on 26/06/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Color;

import de.hdodenhof.circleimageview.CircleImageView;

import com.example.victorbello.androidchat.entities.User;
import com.example.victorbello.androidchat.R;
import com.example.victorbello.androidchat.lib.ImageLoader;
import com.example.victorbello.androidchat.domain.AvatarHelper;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private List<User> contactList;
    private ImageLoader imageLoader;
    private OnItemClickListener onItemClickListener;
    public String messageOnline;
    public String messageOffline;
    public Context context;

    public ContactListAdapter(List<User> contactList, ImageLoader imageLoading, OnItemClickListener onItemClickListener, Context  context){
        this.contactList=contactList;
        this.imageLoader =imageLoading;
        this.onItemClickListener=onItemClickListener;
        this.context=context;
        messageOnline=context.getResources().getString(R.string.contactlist_message_online);
        messageOffline=context.getResources().getString(R.string.contactlist_message_offline);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.content_contact,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,int position ){
        User user=contactList.get(position);
        viewHolder.setClickListener(user,onItemClickListener);

        boolean online=user.isOnline();
        String status=online? messageOnline:messageOffline;
        int color=online? Color.GREEN:Color.RED;
        String email=user.getEmail();

        viewHolder.txtUser.setText(email);
        viewHolder.txtStatus.setText(status);
        viewHolder.txtStatus.setTextColor(color);

        imageLoader.load(viewHolder.imgAvatar, AvatarHelper.getAvatarUrl(email));
    }

    @Override
    public int getItemCount(){
        return contactList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView imgAvatar;
        public TextView txtUser;
        public TextView txtStatus;
        private View view;

        public ViewHolder(View itemView){
            super(itemView);

            imgAvatar=(CircleImageView) itemView.findViewById(R.id.imgAvatar);
            txtUser=(TextView) itemView.findViewById(R.id.txtUser);
            txtStatus=(TextView) itemView.findViewById(R.id.txtStatus);
            view=itemView;
        }

        private void setClickListener(final User user, final OnItemClickListener listener){
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    listener.onItemClick(user);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view){
                    listener.onItemLongClick(user);
                    return false;
                }
            });
        }
    }

    public void add(User user){
        if(!contactList.contains(user)){
            contactList.add(user);
            notifyDataSetChanged();
        }
    }

    public void change(User user){
        if(contactList.contains(user)){
            int index =contactList.indexOf(user);
            contactList.set(index,user);
            notifyDataSetChanged();
        }
    }

    public void remove(User user){
        if(contactList.contains(user)){
            contactList.remove(user);
            notifyDataSetChanged();
        }
    }

}
