package com.example.observer;

/**
 * 好友聊天窗口
 */
public class ChatWindow extends Subscriber {

    private int friendId;
    private String title;

    public ChatWindow(int friendId, String title) {
        this.friendId = friendId;
        this.title = title;

        App.broker.subscribe("u" + friendId, this);

        System.out.println(title + " 窗口打开");
    }

    public void close() {
        App.broker.unSubscribe("u" + friendId, this);
        System.out.println(title + " 窗口关闭");
    }

    @Override
    public void notify(Message message) {
        if (message.getSenderId() != friendId) {
            return;
        }

        System.out.println(title + "\n\t" + message.getContent());
    }

}
