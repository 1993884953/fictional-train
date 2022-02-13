package com.example.observer;

public class App {

    public static Broker broker = new Broker();

    public static void main(String[] args) {
        // 1234  张三
        // 5678  Jack
        // 1001  群

        ChatWindow chat1 = new ChatWindow(1234, "与张三的对话窗口");
        ChatWindow chat2 = new ChatWindow(5678, "与Jack的对话窗口");
        GroupWindow chat3 = new GroupWindow(1001);
        ContactWindow contactWindow = new ContactWindow();

        new Thread(() -> {

            sleep();
            broker.publish(new Message(1234, "你好啊，我是张三", Message.TYPE_USER));

            sleep();
            broker.publish(new Message(5678, "hi, I'm Jack", Message.TYPE_USER));

            sleep();
            chat1.close();

            broker.publish(new Message(1234, "你好啊，我是张三", Message.TYPE_USER));
            broker.publish(new Message(5678, "hi, I'm Jack", Message.TYPE_USER));

            broker.publish(new Message(1001, "群消息11111", Message.TYPE_GROUP));

            chat2.close();
            chat3.close();

        }).start();


    }

    private static void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
