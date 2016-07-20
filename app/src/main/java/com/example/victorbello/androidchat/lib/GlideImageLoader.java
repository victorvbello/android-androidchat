package com.example.victorbello.androidchat.lib;



/**
 * Created by ragnarok on 28/06/16.
 */

import android.widget.ImageView;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public class GlideImageLoader implements ImageLoader{

    private RequestManager requestManager;

    public GlideImageLoader(Context context){
        this.requestManager= Glide.with(context);
    }

    @Override
    public void load(ImageView imageView, String url) {
        requestManager.load(url).into(imageView);
    }
}
