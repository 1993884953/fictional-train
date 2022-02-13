package com.example.gui.service;

import com.example.gui.dao.UserDao;
import com.example.gui.entity.User;
import com.example.gui.entity.UserList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

public class MessageService extends JFrame {
    public List messageList;
    public boolean open;
    int frameWidth = 400;
    int frameHeight = 800;

    public MessageService() throws HeadlessException {
        this.open=true;
        setDefaultCloseOperation(MessageService.DISPOSE_ON_CLOSE);

    }
    public void createMessage(UserList userOther, User user, String token){
        MessageService frame = new MessageService();
        frame.setTitle("联系人列表");
        frame.setBounds(frameWidth*2,frameWidth/2,frameWidth*3/2,frameHeight*4/5);
        frame.setLayout(null);                // 取消窗体的默认布局
//        // 关闭按扭的默认行为设置为销毁窗口
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosed(WindowEvent e) {
                System.out.println(" 窗口关闭");
                open=true;
            }
        });
        int fieldWidth = frameWidth * 3 / 5;
        int fieldHeight = fieldWidth / 5;

        int labelWidth = frameWidth / 5;
        int labelX = 25;
        int labelY = 60;
        int space = 100;

        //账号输入框
        JLabel label1 = new JLabel("用户名:");
        label1.setBounds(labelX, labelY, labelWidth, fieldHeight);
        frame.add(label1);

        JTextField field1 = new JTextField(20);
        field1.setBounds(labelX, labelY * 8, fieldWidth * 5 / 3, fieldHeight);
        frame.add(field1);
        String info1 = "";
        field1.setText(info1);

        JButton button = new JButton("发送");
        int buttonWitch = 200;
        int buttonHeight = 50;
        button.setBounds(buttonWitch * 2 + buttonHeight, labelY * 8, buttonWitch / 2, buttonHeight);

        frame.add(button);                    // 将按扭添加到窗体中

        button.addActionListener(event -> {   // 点击事件
            UserDao userDao = new UserDao();
            System.out.println(userOther + " " + token);
            messageList.add(userOther+token);

        });
        frame.setVisible(true);               // 放在最后面，组件都添加完成之后，才显示主窗体
        this.open=false;
    }


    public static void main(String[] args) {
        MessageService messageService=new MessageService();
        UserList userList=new UserList();

//        messageService.createMessage(userList,);
    }
}

