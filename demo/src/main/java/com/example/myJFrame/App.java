package com.example.myJFrame;

import com.example.myJFrame.controller.TokenController;
import com.example.myJFrame.controller.UserController;
import com.example.myJFrame.dto.RequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        new TokenController().getToken();//获取token
//        new UserController().getUser("db2ea93f62bb55cb1d9c77a78ec47011");//获取user信息
    }
}
