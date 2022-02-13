package com.example.observer;

import java.util.HashMap;
import java.util.Map;


public class ContactWindow  extends Subscriber {

    private Map<Integer, User> users = new HashMap<>();

    public ContactWindow() {
        User user1 = new User(1234, "张三");
        users.put(user1.getId(), user1);
        App.broker.subscribe("u" + user1.getId(), this);

        User user2 = new User(5678, "Jack");
        users.put(user2.getId(), user2);
        App.broker.subscribe("u" + user2.getId(), this);

        System.out.println("联系人窗口");
    }

    @Override
    public void notify(Message message) {
        int senderId = message.getSenderId();
        User u = users.get(senderId);
        u.setUnread(u.getUnread() + 1);
        System.out.println(u.getId() + "未读：" + u.getUnread());
    }


}
