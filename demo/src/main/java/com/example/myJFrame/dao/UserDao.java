package com.example.myJFrame.dao;

import com.example.myJFrame.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UserDao {

    //获取用户信息
    public UserDto getUser(String token) throws IOException {
        // 统一资源定位符对象
        URL url = new URL("https://www.it266.com/teamboard-backend/api/user/profile?token=" + token);

        // 打开一个连接对象，强转成httpURLConnection类
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 设置请求方法为 GET
        conn.setRequestMethod("GET");

        // 通过连接对象，获取一个输入流
        InputStream inputStream = conn.getInputStream();

        // 将输入流转为 BufferedReader对象，以字符行的方式读据数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));


        UserDto userDto = null;
        // 循环读取数据
        while (true) {
            String row = reader.readLine();

            if (row == null) {  // 没有数据了
                break;
            }
            ObjectMapper mapper = new ObjectMapper();
            userDto = mapper.readValue(row, UserDto.class);
            System.out.println(row);
        }

        // 关闭相关资源
        reader.close();
        inputStream.close();
        conn.disconnect();
        return userDto;
    }


}
