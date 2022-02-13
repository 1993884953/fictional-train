package com.example.myJFrame.service;

import com.example.myJFrame.dao.UserDao;
import com.example.myJFrame.dao.UserListDao;
import com.example.myJFrame.entity.Message;
import com.example.myJFrame.entity.User;
import com.example.myJFrame.entity.UserList;
import com.example.myJFrame.util.MyButton;
import com.example.myJFrame.util.MyJFrame;
import com.example.myJFrame.util.MyJLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    public String getUser(String token) throws IOException {
        int x = 20;
        int y = 30;
        int width = 100;
        int height = 50;
        int space = 80;
        //设置窗口
        MyJFrame frame = new MyJFrame("联系人列表", 200, 200, 400, space * 10);
        User user = new UserDao().getUser(token).getData();
        List<UserList> userList = new ArrayList<>(new UserListDao().getUserList(token).getData());

        //设置图片
        URL myAvatarUrl = new URL(user.getAvatar_url());
        ImageIcon myAvatar = new ImageIcon(myAvatarUrl);
        myAvatar.setImage(myAvatar.getImage().getScaledInstance(space, space, Image.SCALE_DEFAULT));
        //设置图片尺寸，图片位置
        JLabel avatarLabel = new JLabel(myAvatar);
        avatarLabel.setBounds(space / 2, space / 2, space, space);
        frame.add(avatarLabel);
        //昵称uid输出
        frame.add(new MyJLabel(user.getNickname(), "微软雅黑", Font.BOLD, 18, space * 3 / 2 + x, x + y, width, y));
        frame.add(new MyJLabel("uid: " + user.getUid(), "微软雅黑", Font.PLAIN, 14, space * 3 + x, x + y, width, y));

        frame.add(new MyJLabel(user.getUsername(), "微软雅黑", Font.BOLD, 14, space * 3 / 2 + x, 3 * x + y, width, y));
        frame.add(new MyJLabel(user.getEmail(), "微软雅黑", Font.PLAIN, 12, space * 3, 3 * x + y, width * 3 / 2, y));

        System.out.println(token);

        userList.forEach(item -> {
            try {

                int index = userList.indexOf(item);
                System.out.println(index + "," + item);
                //设置图片
                URL otherAvatarUrl = new URL(item.getAvatar_url());
                ImageIcon otherAvatar = new ImageIcon(otherAvatarUrl);
                otherAvatar.setImage(otherAvatar.getImage().getScaledInstance(space, space, Image.SCALE_DEFAULT));
                //设置图片尺寸，图片位置
                JLabel otherAvatarLabel = new JLabel(otherAvatar);
                otherAvatarLabel.setBounds(space / 2, space * 2 + index * space - 10, space * 3 / 4, space * 3 / 4);
                frame.add(otherAvatarLabel);


                //昵称uid输出
                frame.add(new MyJLabel(item.getNickname(), "微软雅黑", Font.BOLD, 18, space * 3 / 2, space * 2 + index * space, width, y));
//                frame.add(new MyJLabel("uid: " + item.getUid(), "微软雅黑", Font.PLAIN, 14, space * 3 + x, space *2+index*space, width, y));
                frame.add(new MyJLabel("uid: " + item.getUid(), "微软雅黑", Font.PLAIN, 13, space * 3 / 2, space * 2 + y + index * space, width, y));

                //聊一聊按钮
                MyButton myButton = new MyButton("聊一聊", space * 3 + x, space * 2 + index * space, width, height);

                MyJLabel messageTip = new MyJLabel(0, space * 3, space * 2+10 + index * space, 20,  30);
                messageTip.setOpaque(true);
                messageTip.setHorizontalAlignment(JLabel.CENTER);
                messageTip.setBackground(Color.PINK);
                frame.add(myButton);
                frame.add(messageTip);

                //点击加载聊天窗口
                Message message = Message.builder().type(1).receiver_id(item.getId()).receiver_type(1).build();
                MessageService messageService = new MessageService(message, user, userList, token, messageTip);
                System.out.println(messageService.getMessageList().size());


                Thread thread = new Thread(() -> {

                    myButton.addActionListener(event -> {
                        messageTip.setText(String.valueOf(0));
                        messageTip.repaint();
                        messageService.setVisible(!messageService.isVisible());
                        messageService.refresh();
                    });
                });
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        frame.setVisible(true);               // 放在最后面，组件都添加完成之后，才显示主窗体
        return "联系人列表加载成功";
    }
}
