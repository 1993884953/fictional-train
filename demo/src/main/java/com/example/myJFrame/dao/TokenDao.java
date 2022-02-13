package com.example.myJFrame.dao;

import com.example.myJFrame.dto.TokenDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TokenDao {
    //登录的接口
    public TokenDto getToken(String username, String password) throws IOException {
        // 统一资源定位符对象
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
//        String data = "username="+username+"&password="+password;
        String data = "username=" + "jack123B" + "&password=" + "cw20000526";

        outputStream.write(data.getBytes());

        // 服务端返回的HTTP状态码
        System.out.println(conn.getResponseCode());

        // 通过连接对象，获取一个输入流
        InputStream inputStream = conn.getInputStream();

        // 将输入流转为 BufferedReader对象，以字符行的方式读据数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        // 循环读取数据
        TokenDto tokenDto = null;
        while (true) {

            String row = reader.readLine();

            if (row == null) {  // 没有数据了
                break;
            }

//            System.out.println(row);

            ObjectMapper mapper = new ObjectMapper();

            tokenDto = mapper.readValue(row, TokenDto.class);

        }

        // 关闭相关资源
        outputStream.close();
        reader.close();
        inputStream.close();
        conn.disconnect();
        return tokenDto;
    }

}
