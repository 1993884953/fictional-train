package com.example.myJFrame.service;

import com.example.myJFrame.dao.MessageDao;
import com.example.myJFrame.dto.MessageDto;
import com.example.myJFrame.dto.RequestDto;
import com.example.myJFrame.entity.Content;
import com.example.myJFrame.entity.Message;
import com.example.myJFrame.entity.User;
import com.example.myJFrame.entity.UserList;
import com.example.myJFrame.util.MyButton;
import com.example.myJFrame.util.MyJLabel;
import com.example.myJFrame.util.MyJTextField;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.springframework.beans.BeanUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@EqualsAndHashCode(callSuper = true)
@Data
public class MessageService extends JFrame {

    protected  List<Message> messageList = new ArrayList<>();
    private Map<Integer, UserList> userOther = new HashMap<>();
    private JPanel jPanel = new JPanel();
    private String text;//文本域信息
    private User user;
    private String token;
    private MyJLabel messageTip;
    private Message messageNow;
    //加载配置
    public MessageService(Message message, User user, List<UserList> userOther, String token,MyJLabel messageTip) throws IOException {
        //复制到类里面使用
        this.user = user;
        this.token=token;
        this.messageNow=message;
        this.messageTip=messageTip;
        UserList myUser = new UserList();
        BeanUtils.copyProperties(user, myUser);
        this.userOther.put(user.getId(), myUser);
        //搜集所有用户信息
        for (UserList obj : userOther) {
            this.userOther.put(obj.getId(), obj);
        }

        int width = 600;
        int height = 700;
        String nickname = null;
        for (UserList userList : userOther) {
            if (message.getReceiver_id()==userList.getId()){
                nickname=userList.getNickname();
            }
        }
        setTitle("与"+nickname+"聊天窗口");
        setLayout(null);                // 取消窗体的默认布局
        setBounds(600, 200, width, height);
        //画板工具
        jPanel.setBounds(0, 0, width - 20, width * 3 / 4);
        jPanel.setBackground(Color.white);
        this.getMessage(user.getId(), token);
        add(jPanel);
        //窗口改变事件
        addComponentListener(new ComponentAdapter() {//让窗口响应大小改变事件
            public void componentResized(ComponentEvent e) {
                refresh();
            }
        });

        //文本域
        MyJTextField myJTextField = new MyJTextField(text, 40, 10, height - 250, width - 40, 140);
        add(myJTextField);
        //发送按钮
        MyButton myButton = new MyButton("发送", width - 120, height - 100, 80, 40);
        add(myButton);

        //键盘按下事件
        Thread thread1 = new Thread(() -> {
            myJTextField.addActionListener(event -> {
                createMessage(myJTextField,message);
            });
            myButton.addActionListener(event -> {
                createMessage(myJTextField,message);
            });
        });
        thread1.start();

        refresh();
        setVisible(false);               // 放在最后面，组件都添加完成之后，才显示主窗体
    }

    //重新加载画布
    public void refresh() {
//        Thread thread=new Thread(()->{
            jPanel.removeAll();
            int limit = 7;
            List<Message> messages = messageList.subList(Math.max(messageList.size() - limit, 0), messageList.size());
            messages.forEach(item -> {
                UserList userList = userOther.get(item.getSender_id());
                int index = messages.indexOf(item);
                try {
                    //设置图片
                    URL myAvatarUrl = new URL(userList.getAvatar_url());
                    ImageIcon myAvatar = new ImageIcon(myAvatarUrl);
                    myAvatar.setImage(myAvatar.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
                    //设置图片尺寸，图片位置
                    JLabel avatarLabel = new JLabel(myAvatar);
                    avatarLabel.setBounds(10 + (user.getId() == item.getSender_id() ? 500 : 0), 30 + index * 50, 30, 30);

                    MyJLabel myJLabel = new MyJLabel(item.getContent().getText(), 70, 30 + index * 50, 420, 40);
                    myJLabel.setBackground(Color.PINK);
                    if (user.getId() == item.getSender_id()) {
                        myJLabel.setHorizontalAlignment(JLabel.RIGHT);
                    }
                    jPanel.add(avatarLabel);
                    jPanel.add(myJLabel);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            });
            jPanel.repaint();
//        });
//        thread.start();

    }

    //订阅消息
    private void getMessage(int id, String token) {
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
                    System.out.println(messageNow.getReceiver_id()+"AAA"+requestDto.getPayload().getMessage().getSender_id());
                    if(messageNow.getReceiver_id()==requestDto.getPayload().getMessage().getSender_id()){
                        this.messageList.add(requestDto.getPayload().getMessage());
                        //未读消息加一
                        if(!isVisible()){
                            this.messageTip.setText(String.valueOf(1+Integer.parseInt(messageTip.getText())));
                            this.messageTip.repaint();
                        }
                    }
                    System.out.println("________________________________________________________________");
                    message.ack();
                    refresh();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread1.start();
    }
    //创建消息
    private void createMessage(MyJTextField myJTextField,Message message){

        //消息发送
        Message messageCreate = new Message();
        BeanUtils.copyProperties(message, messageCreate);
        if (myJTextField.getText().length() == 0) {
            JOptionPane.showConfirmDialog(MessageService.this, "不能发送空消息", "发送失败", JOptionPane.YES_NO_OPTION);
            return;
        }
        messageCreate.setContent(new Content(myJTextField.getText()));
        MessageDto messageDto;
        try {
            messageDto = new MessageDao().createMessage(messageCreate, token);
            if (!messageDto.isStatus()) {
                JOptionPane.showConfirmDialog(MessageService.this, messageDto.getData(), "发送失败", JOptionPane.YES_NO_OPTION);
                return;
            }

            System.out.println("消息发送成功" + messageCreate);
            //添加自己发送的信息
            Message message1 = new Message();
            BeanUtils.copyProperties(messageCreate, message1);
            message1.setSender_id(user.getId());
            this.messageList.add(messageList.size(), message1);
            myJTextField.setText("");
        } catch (IOException e) {
//                e.printStackTrace();
            JOptionPane.showConfirmDialog(MessageService.this, "格式错误发送失败", "发送失败", JOptionPane.YES_NO_OPTION);
        }
//            System.out.println(e.get());
        refresh();
    }
}

