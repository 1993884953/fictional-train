package com.example.java.jiemianbao;


import com.fasterxml.jackson.databind.ObjectMapper;
import dto.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

//qq登录界面
//输入密码账户，获取token给到下一个界面
public class Denglu {
    public String token;

    //    生成界面，方法不需要传入参数
//    在主程序调用此方法开始运行程序
    public void makedenglu() {
        JFrame frame = new JFrame();
        frame.setTitle("Hello");
        frame.setSize(300, 400);
        frame.setLayout(null);// 取消窗体的默认布局

        JLabel label1 = new JLabel("用户名");
        label1.setBounds(20, 20, 60, 30);
        frame.add(label1);
        JTextField field1 = new JTextField(20);
        field1.setBounds(80, 20, 200, 30);
        frame.add(field1);

        JLabel label2 = new JLabel("密码");
        label2.setBounds(20, 80, 60, 30);
        frame.add(label2);
        JTextField field2 = new JTextField(20);
        field2.setBounds(80, 80, 200, 30);
        frame.add(field2);

        JButton button = new JButton("登录");
        button.setBounds(80, 160, 60, 30);     // x, y, width, height

        button.addActionListener(event -> {   // 点击事件
            if (field1.getText().isEmpty() || field2.getText().isEmpty()) {
                int res = JOptionPane.showConfirmDialog(frame, "密码或用户名为空", "错误提示", 0);
            } else {
                try {
                    this.postlogin(field1.getText(), field2.getText());

                    Homejiemian homejiemian = new  Homejiemian(this.token);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //跳转
                frame.dispose();
            }
        });


        frame.add(button);                    // 将按扭添加到窗体中

        frame.setVisible(true);               // 放在最后面，组件都添加完成之后，才显示主窗体

    }

    public void postlogin(String username, String password) throws IOException {
        URL url = new URL("https://www.it266.com/teamboard-backend/api/user/token");

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
        String data = "username=" + username + "&password=" + password;
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

            TokenDto tokenDto = mapper.readValue(row, TokenDto.class);

            this.token=tokenDto.getData().getToken();
        }

        // 关闭相关资源
        outputStream.close();
        reader.close();
        inputStream.close();
        conn.disconnect();

    }

    //收到的token消息




}
