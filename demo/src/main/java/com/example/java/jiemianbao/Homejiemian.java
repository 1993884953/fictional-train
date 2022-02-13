package com.example.java.jiemianbao;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import org.fusesource.mqtt.client.*;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class Homejiemian {
//    属性 本人信息类
//    订阅类列表《用户id，好友对象？ 》
//    消息列表==《时间，消息》


//    执行顺序 获取好友列表 创建好友聊天记录列表 建立链接
//    收到消息时 消息加到用户消息列表中 未读加一
//    打开对话框，好友对象未读消息清0，显示全部聊天信息

    //此文件相当与老师订阅参考中的APP.java
//先new出来一个发布订阅相关 的类出来
    public static Broker broker = new Broker();
    //
    public String token;
    public User user;
    public List<Context> ContextList;

    //    构造方法
    public Homejiemian(String token) throws IOException {
        this.token = token;
//        获取自身消息 调用本身方法执行
        getUserDto();
        //       获得好友信息
        getfrininfor();
//        显示界面信息
        homeWindow();
//建立练习
        linedomn();

    }

    //    获取自身消息并存入User中
    public void getUserDto() throws IOException {
        URL url = new URL("https://www.it266.com/teamboard-backend/api/user/profile");

        // 打开一个连接对象，强转成httpURLConnection类
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 设置请求方法为 POST
        conn.setRequestMethod("GET");

        // 需要向流中写入数据时，设置为true
        conn.setDoOutput(true);

        // 设置Content-Type
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        OutputStream outputStream = conn.getOutputStream();
        // 需要发送的数据
        String data = "token=" + token;
        outputStream.write(data.getBytes());
        // 服务端返回的HTTP状态码

        // 通过连接对象，获取一个输入流
        InputStream inputStream = conn.getInputStream();
        // 将输入流转为 BufferedReader对象，以字符行的方式读据数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        // 循环读取数据
        while (true) {
            String row = reader.readLine();
            if (row == null) {  // 没有数据了
                break;
            }

//            System.out.println("1"+
//            String[] s1=row.split("\"data\":");
////            String[] s2=s1[1].split("\\}");
//            System.out.println("s1=="+s1[1]);
//            String[] s2=s1[1].split(",\"code\":\"");
//            System.out.println("s102=="+s2[0]);
//            String json = s2[0];
//
//            ObjectMapper mapper = new ObjectMapper();
            ObjectMapper mapper = new ObjectMapper();

            UserDto userDto = mapper.readValue(row, UserDto.class);
            this.user = userDto.getData();
        }

        // 关闭相关资源
        outputStream.close();
        reader.close();
        inputStream.close();
        conn.disconnect();

    }

    //    获取好友消息并存入User
    public void getfrininfor() throws IOException {
        URL url = new URL("https://www.it266.com/teamboard-backend/api/chat/contact");

        // 打开一个连接对象，强转成httpURLConnection类
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 设置请求方法为 POST
        conn.setRequestMethod("GET");

        // 需要向流中写入数据时，设置为true
        conn.setDoOutput(true);

        // 设置Content-Type
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        OutputStream outputStream = conn.getOutputStream();
        // 需要发送的数据
        String data = "token=" + token;
        outputStream.write(data.getBytes());
        // 服务端返回的HTTP状态码

        // 通过连接对象，获取一个输入流
        InputStream inputStream = conn.getInputStream();
        // 将输入流转为 BufferedReader对象，以字符行的方式读据数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        // 循环读取数据
        while (true) {
            String row = reader.readLine();
            if (row == null) {  // 没有数据了
                break;
            }

            ObjectMapper mapper = new ObjectMapper();

            ContextDto contactDto = mapper.readValue(row, ContextDto.class);
            this.ContextList = contactDto.getData();

        }

        // 关闭相关资源
        outputStream.close();
        reader.close();
        inputStream.close();
        conn.disconnect();
    }

    //    显示界面信息
    public void homeWindow() {
        JFrame frame = new JFrame();

        frame.setTitle("个人界面");
        frame.setSize(400, 600);

        JPanel userPanel = new JPanel();//画笔
        userPanel.setLayout(null);
        userPanel.setBounds(300, 10, 300, 100);
        ImageUtil.renderAvatarUrlStrToPanel(user.getAvatar_url(), userPanel, 0, 0, 50, 50);
        frame.add(userPanel);
        JLabel label1 = new JLabel("用户名:  " + user.getNickname());
        label1.setBounds(20, 20, 200, 30);
        frame.add(label1);
//
        JLabel label2 = new JLabel("id:  " + user.getId());
        label2.setBounds(20, 60, 200, 30);
        frame.add(label2);

        JLabel label3 = new JLabel("-----------------------好友列表----------------------");
        label3.setBounds(20, 100, 300, 40);
        frame.add(label3);

        for (int i = 0; i < ContextList.size(); i++) {
//            id,好友对象
            broker.subscribe(ContextList.get(i).getId(), new FriendWindow(ContextList.get(i), frame, i, token));

        }

        frame.setLayout(null);                // 取消窗体的默认布局
        frame.setVisible(true);


    }

    //    启动监听事件
    private void linedomn() {
        Thread thread1 = new Thread(() -> {
            // 这里面的代码，将在单独的线程中运行
            MQTT mqtt = new MQTT();
            try {
                mqtt.setHost("mqtt.it266.com", 1883);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            mqtt.setUserName(String.valueOf(user.getId()));
            mqtt.setPassword(token);

//            建立链接
            BlockingConnection connection = mqtt.blockingConnection();

            connection.connect();

            Topic[] topics = {new Topic("/msg/u/" + user.getId(), QoS.AT_LEAST_ONCE)};

            byte[] qoses = connection.subscribe(topics);


            while (true) {
                Message message = null;

                message = connection.receive();

                byte[] payload = message.getPayload();

                String row = new String(payload);
                ObjectMapper mapper = new ObjectMapper();

                RequstDto requstDto = mapper.readValue(row, RequstDto.class);
//   获得到的回复消息
                broker.publish(requstDto.getPayload().getMessage());

                message.ack();
            }
        });

        thread1.start();
    }
}
