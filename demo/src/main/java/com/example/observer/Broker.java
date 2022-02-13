package com.example.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Broker {

    /**
     * {
     * u1234: [ChatWindow],
     * u5678: [ChatWindow],
     * g1001: [GroupWindow],
     * }
     */
    private Map<String, List<Subscriber>> subscribers = new HashMap<>();

    // 订阅
    public void subscribe(String topic, Subscriber subscriber) {
        List<Subscriber> observerList = this.subscribers.get(topic);
        if (observerList == null) {
            observerList = new ArrayList<>();
            subscribers.put(topic, observerList);
        }
        observerList.add(subscriber);
    }

    // 取消订阅
    public void unSubscribe(String topic, Subscriber observer) {
        subscribers.get(topic).remove(observer);
    }

    // 发布
    public void publish(Message message) {
        notifyAllObservers(message);
    }

    // 通知订阅者
    private void notifyAllObservers(Message message) {

        String topic = (message.getType() == Message.TYPE_GROUP ? "g" : "u") + message.getSenderId();

        for (Subscriber subscriber : subscribers.get(topic)) {
            subscriber.notify(message);
        }
    }
}
