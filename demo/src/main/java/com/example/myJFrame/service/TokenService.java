package com.example.myJFrame.service;

import com.example.myJFrame.controller.UserController;
import com.example.myJFrame.dao.TokenDao;
import com.example.myJFrame.dto.TokenDto;
import com.example.myJFrame.util.MyButton;
import com.example.myJFrame.util.MyJFrame;
import com.example.myJFrame.util.MyJLabel;
import com.example.myJFrame.util.MyJTextField;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

//获取用户界面
public class TokenService {
    public String getToken() {
        int x = 20;
        int y = 30;
        int width = 100;
        int height = 50;
        int space = 80;
        //设置窗口
        MyJFrame frame = new MyJFrame("登录界面", 200, 200, 400, space * 10);

        //账号、密码输入框

        frame.add(new MyJLabel("用户账号: ", "微软雅黑", Font.BOLD, 16, x, y + space, width, height));
        MyJTextField username = new MyJTextField(20, x + space, y + space, width * 2 + height, height);
        frame.add(username);

        frame.add(new MyJLabel("用户密码: ", "微软雅黑", Font.BOLD, 16, x, y + space * 2, width, height));
        MyJTextField password = new MyJTextField(20, x + space, y + space * 2, width * 2 + height, height);

        frame.add(password);

        //登录按钮
        MyButton myButton = new MyButton("点击登录", x + space, space * 6, width * 2, height);
        frame.add(myButton);
        //点击事件
        myButton.addActionListener(event -> {
            try {
                if (username.getText().equals("") || password.getText().equals("")) {
                    JOptionPane.showConfirmDialog(frame, "未输入用户名或密码", "登录提示", JOptionPane.YES_NO_OPTION);
                    return;
                }
                TokenDto tokenDto = new TokenDao().getToken(username.getText(), password.getText());
                System.out.println(tokenDto);
                if (!tokenDto.isStatus()) {
                    JOptionPane.showConfirmDialog(frame, tokenDto.getData().getToken(), "登录提示", JOptionPane.YES_NO_OPTION);
                    return;
                }
                new UserService().getUser(tokenDto.getData().getToken());
                frame.dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        frame.setVisible(true);               // 放在最后面，组件都添加完成之后，才显示主窗体
        return "登录界面启动成功";
    }
}
