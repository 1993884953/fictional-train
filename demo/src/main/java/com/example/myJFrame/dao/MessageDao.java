package com.example.myJFrame.dao;

import com.example.myJFrame.dto.MessageDto;
import com.example.myJFrame.dto.RequestDto;
import com.example.myJFrame.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.springframework.beans.BeanUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class MessageDao {
    private static void getMessage(int id, String token) {
        Thread thread1 = new Thread(() -> {
            // 这里面的代码，将在单独的线程中运行
            MQTT mqtt = new MQTT();

            try {
                mqtt.setHost("mqtt.it266.com", 1883);

                mqtt.setUserName(String.valueOf(id));
                mqtt.setPassword(token);

//            建立链接
                BlockingConnection connection = mqtt.blockingConnection();

                connection.connect();

                Topic[] topics = {new Topic("/msg/u/" + id, QoS.AT_LEAST_ONCE)};
//                connection.publish("foo", "Hello".getBytes(), QoS.AT_LEAST_ONCE, false);

                byte[] qoses = connection.subscribe(topics);


                while (true) {
                    org.fusesource.mqtt.client.Message message;
                    message = connection.receive();
                    byte[] payload = message.getPayload();

                    String row = new String(payload);
                    ObjectMapper mapper = new ObjectMapper();
                    RequestDto requestDto = mapper.readValue(row, RequestDto.class);
//   获得到的回复消息
                    System.out.println("________________________________________________________________");

                    System.out.println(requestDto.getPayload().getMessage());
//                    broker.publish(requestDto.getPayload().getMessage());
                    System.out.println("________________________________________________________________");
                    message.ack();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread1.start();
    }

    public static void main(String[] args) throws Exception {
        getMessage(10816, "7721030767e0016e94a80bb9e1cebb92");

    }

    public MessageDto createMessage(Message message, String token) throws IOException {
        URL url = new URL("https://www.it266.com/teamboard-backend/api/chat/message/create?token=" + token);

        // 打开一个连接对象，强转成httpURLConnection类
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 设置请求方法为 POST
        conn.setRequestMethod("POST");

        // 需要向流中写入数据时，设置为true
        conn.setDoOutput(true);

        // 设置Content-Type
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        OutputStream outputStream = conn.getOutputStream();
        // 需要发送的数据
        String data = "content=" + message.getContent().getText() + "&receiver_id=" + message.getReceiver_id() + "&type=1&receiver_type=1";


        outputStream.write(data.getBytes());
        // 服务端返回的HTTP状态码
//
        // 通过连接对象，获取一个输入流
        InputStream inputStream = conn.getInputStream();
        // 将输入流转为 BufferedReader对象，以字符行的方式读据数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        // 循环读取数据
        MessageDto messageDto = new MessageDto();
        while (true) {
            String row = reader.readLine();
            if (row == null) {  // 没有数据了
                break;
            }
            ObjectMapper mapper = new ObjectMapper();
//            try {
            messageDto = mapper.readValue(row, MessageDto.class);
//            } catch (RuntimeException e) {
//                System.out.println("============================");
//                e.printStackTrace();
//                System.out.println("===============================");
//            }


        }
        // 关闭相关资源
        outputStream.close();
        reader.close();
        inputStream.close();
        conn.disconnect();

        return messageDto;
    }
}
