package com.example.gui.service;

import com.example.gui.dao.UserDao;
import com.example.gui.entity.User;
import com.example.gui.entity.UserList;
import com.example.gui.util.TokenFoo;
import com.example.gui.util.UserFoo;
import com.example.gui.util.UserListFoo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService  extends  JFrame{

    //登录服务，登录成功则调用个人信息也
    public String login() {
        int frameWidth = 400;
        int frameHeight = 800;
        JFrame frame = new JFrame( );
        frame.setTitle("Hello");
//        frame.setSize(frameWidth, frameHeight);
        frame.setBounds(frameWidth,frameWidth/2,frameWidth,frameHeight);

        // 关闭按扭的默认行为设置为销毁窗口
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 如果我们希望在在窗口关闭时，得到通知：
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                System.out.println(" 窗口关闭");
            }
        });

        // 如果希望点击窗口时退出整个应用程序，可以使用：
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

        frame.setLayout(null);                // 取消窗体的默认布局

        JButton button = new JButton("点我登录");
        int buttonWitch = 200;
        int buttonHeight = 50;
        button.setBounds((frameWidth - buttonWitch) / 2, frameHeight - frameWidth / 2, buttonWitch, buttonHeight);     // x, y, width, height

        frame.add(button);                    // 将按扭添加到窗体中


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
        field1.setBounds(labelX + labelY, labelY, fieldWidth, fieldHeight);
        frame.add(field1);
        String info1 = "";
        field1.setText(info1);
        //密码输入框
        JLabel label2 = new JLabel("密码:");
        label2.setBounds(labelX, labelY + space, labelWidth, fieldHeight);
        frame.add(label2);


        JTextField field2 = new JTextField(20);
        field2.setBounds(labelX + labelY, labelY + space, fieldWidth, fieldHeight);

        String info2 = "";
        field2.setText(info2);
        frame.add(field2);

        button.addActionListener(event -> {   // 点击事件
            try {
                if (field1.getText().length() == 0 || field2.getText().length() == 0) {
                    int res = JOptionPane.showConfirmDialog(frame, "未输入用户名或密码", "登录提示", JOptionPane.YES_NO_OPTION);
                } else {
                    UserDao userDao = new UserDao();
                    TokenFoo tokenFoo = userDao.login(field1.getText(), field2.getText());
                    if (tokenFoo.getCode().equals("0")) {
                        int res = JOptionPane.showConfirmDialog(frame, "登陆成功,是否去首页？", "登录提示", JOptionPane.YES_NO_OPTION);
                        if (res == 0) {
                            System.out.println(res);

                            UserService userService = new UserService();
                            userService.getMessage(tokenFoo.getData().getToken());
//                        frame.setDefaultCloseOperation(JFrame.);
                            frame.dispose();
                        }
                    } else {
                        int res = JOptionPane.showConfirmDialog(frame, tokenFoo.getData().getToken(), "登录提示", JOptionPane.YES_NO_OPTION);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);               // 放在最后面，组件都添加完成之后，才显示主窗体
        return "启动成功";
    }


    public void getMessage(String token) throws IOException {
        System.out.println(token);
        UserDao userDao = new UserDao();
        UserFoo userFoo = userDao.getUser(token);
        User user = userFoo.getData();

        int frameWidth = 400;
        int frameHeight = 800;
        JFrame frame = new JFrame();
        frame.setTitle("联系人列表");
        frame.setBounds(frameWidth,frameWidth/2,frameWidth,frameHeight);
        frame.setLayout(null);                // 取消窗体的默认布局
        // 关闭按扭的默认行为设置为销毁窗口
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 如果我们希望在在窗口关闭时，得到通知：
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                System.out.println(" 窗口关闭");
            }
        });
        //设置图片
        int AVATAR_WIDTH = 80;
        int AVATAR_HEIGHT = 80;
        URL avatarUrl = new URL(user.getAvatar_url());
        ImageIcon userIcon = new ImageIcon(avatarUrl);
        userIcon.setImage(userIcon.getImage().getScaledInstance(AVATAR_WIDTH, AVATAR_HEIGHT, Image.SCALE_DEFAULT));

        //设置图片尺寸，图片位置
        JLabel avatarLabel = new JLabel(userIcon);
        avatarLabel.setBounds(AVATAR_WIDTH / 3, AVATAR_HEIGHT / 3, AVATAR_WIDTH, AVATAR_HEIGHT);
        frame.add(avatarLabel);

        //设置nickname尺寸、位置
        JLabel nicknameLabel = new JLabel("昵称：" + user.getNickname());
        nicknameLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        nicknameLabel.setBounds(AVATAR_WIDTH * 3 / 2, AVATAR_HEIGHT / 2, AVATAR_WIDTH * 2, AVATAR_HEIGHT / 3);
        frame.add(nicknameLabel);

        //设置username尺寸、位置
        JLabel usernameLabel = new JLabel(user.getUsername());
        usernameLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
        usernameLabel.setBounds(AVATAR_WIDTH * 3 / 2, AVATAR_HEIGHT, AVATAR_WIDTH * 2, AVATAR_HEIGHT / 4);
        frame.add(usernameLabel);
        //uid、位置
        JLabel uidLabel = new JLabel("uid：" + user.getUid());
        uidLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        uidLabel.setBounds(AVATAR_WIDTH * 3, AVATAR_HEIGHT, AVATAR_WIDTH * 2, AVATAR_HEIGHT / 4);
        frame.add(uidLabel);


        // 如果希望点击窗口时退出整个应用程序，可以使用：
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

        //获取联系人列表
        UserListFoo userListFoo = userDao.getUserList(token);
        List<UserList> userList = Arrays.asList(userListFoo.getData());



        userList.forEach(item -> {
            int index = userList.indexOf(item);
            System.out.println(index + "、" + item);


            //设置图片
            try {
                URL avatarOtherUrl = new URL(item.getAvatar_url());

                ImageIcon userOtherIcon = new ImageIcon(avatarOtherUrl);
                userOtherIcon.setImage(userOtherIcon.getImage().getScaledInstance(AVATAR_WIDTH, AVATAR_HEIGHT, Image.SCALE_DEFAULT));

                //设置图片尺寸，图片位置
                JLabel avatarOtherLabel = new JLabel(userOtherIcon);
                avatarOtherLabel.setBounds(AVATAR_WIDTH / 3, AVATAR_HEIGHT *(index+2), AVATAR_WIDTH * 2 / 3, AVATAR_HEIGHT * 2 / 3);
                frame.add(avatarOtherLabel);

                //设置nickname尺寸、位置
                JLabel nicknameOtherLabel = new JLabel("昵称：" + item.getNickname());
                nicknameOtherLabel.setFont(new Font("微软雅黑", Font.PLAIN, 17));
                nicknameOtherLabel.setBounds(AVATAR_WIDTH*5/4 , AVATAR_HEIGHT *(index+2), AVATAR_WIDTH * 2, AVATAR_HEIGHT / 3);
                frame.add(nicknameOtherLabel);

                //uid、位置
                JLabel uidOtherLabel = new JLabel("uid：" + item.getUid());
                uidOtherLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
                uidOtherLabel.setBounds(AVATAR_WIDTH*5/4, AVATAR_HEIGHT *(index+2)+AVATAR_HEIGHT/3+AVATAR_HEIGHT/10, AVATAR_WIDTH * 2, AVATAR_HEIGHT / 4);
                frame.add(uidOtherLabel);

                JButton button = new JButton("聊一聊");

                button.setBounds(frameWidth - AVATAR_WIDTH*2, AVATAR_HEIGHT *(index+2)+AVATAR_HEIGHT/8, AVATAR_WIDTH*3/2, AVATAR_WIDTH/2);     // x, y, width, height

                MessageService messageService =new MessageService( );
                button.addActionListener(event -> {
                    if (messageService.open){
                        messageService.createMessage(item,  user,token);
                    }
                });
                frame.add(button);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });

        frame.setVisible(true);               // 放在最后面，组件都添加完成之后，才显示主窗体
    }
}

