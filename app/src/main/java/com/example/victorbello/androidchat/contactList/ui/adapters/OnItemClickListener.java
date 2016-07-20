package com.example.victorbello.androidchat.contactList.ui.adapters;

/**
 * Created by ragnarok on 26/06/16.
 */

import com.example.victorbello.androidchat.entities.User;

public interface OnItemClickListener {
    void onItemClick(User user);
    void onItemLongClick(User user);
}
