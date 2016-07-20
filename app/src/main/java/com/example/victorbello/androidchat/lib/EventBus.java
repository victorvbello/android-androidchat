package com.example.victorbello.androidchat.lib;

/**
 * Created by ragnarok on 24/06/16.
 */
public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);

}
