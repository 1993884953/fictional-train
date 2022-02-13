package com.example.java.jiemianbao;


import dto.MessageDto;
import dto.PayloadDto;
import org.apache.logging.log4j.message.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//此类负责接受消息并存储对象，通知对象并调用方法
//应具备增加功能 以方便在home类中调用订阅功能 取消订阅功能（没办法删除好友所以不会调用）  通知订阅者即接受消息
public class Broker {
    private Map<String, List<FriendWindow>> subscribers = new HashMap<>();


    //    订阅
    public void subscribe(String id, FriendWindow subscriber) {

//    根据名字获得列表
        List<FriendWindow> observerList = this.subscribers.get(id);
        if (observerList == null) {
            observerList = new ArrayList<>();
            subscribers.put(id, observerList);
        }
        observerList.add(subscriber);
    }

    //发布消息。此功能用于mqtt
    public void publish(MessageDto message) {
        notifyAllObservers(message);
//        通知订阅者
    }

    //通知订阅者，此功能用来调用好友对象中的显示消息功能
    private void notifyAllObservers(MessageDto message) {

//        组装topic内容根据message的type区分群消息或者个人消息
        String topic = (message.getSender_id());
        System.out.println("topic");
        System.out.println(topic);
//这种循环的格式for（循环变量类型   变量名：变量对象）
        for (FriendWindow subscriber : subscribers.get(topic)) {
//            System.out.println(subscriber.context.getNickname());
            subscriber.notifyA(message);
        }
    }
}







