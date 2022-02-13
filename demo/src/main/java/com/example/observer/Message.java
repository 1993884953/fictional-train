package com.example.observer;

/**
 * 聊天消息
 */
public class Message {

    public final static int TYPE_USER = 1;
    public final static int TYPE_GROUP = 2;

    // 消息发送者
    private int senderId;

    // 消息内容
    private String content;

    // 消类型 1用户消息  2群消息
    private int type;

    public Message(int senderId, String content, int type) {
        this.senderId = senderId;
        this.content = content;
        this.type = type;
    }


    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
