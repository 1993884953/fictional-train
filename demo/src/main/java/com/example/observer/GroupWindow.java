package com.example.observer;

/**
 * 群聊窗口
 */
public class GroupWindow extends Subscriber {
    private int groupId;

    public GroupWindow(int groupId) {
        this.groupId = groupId;
        App.broker.subscribe("g" + groupId, this);
    }

    @Override
    public void notify(Message message) {
        System.out.println("群聊窗口收到消息：" + message.getContent());
    }

    public void close() {
        App.broker.unSubscribe("g" + groupId, this);

        System.out.println("群聊" + groupId + " 窗口关闭");
    }

}
