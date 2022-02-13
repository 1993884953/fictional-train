package com.example.observer;

/**
 * 订阅者
 */
public abstract class Subscriber {
    public abstract void notify(Message message);
}
