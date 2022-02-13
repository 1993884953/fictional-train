package com.example.java.jiemianbao;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//此类为好友类从home界面调用
//此类new出对象具备点击事件新增窗口功能
//对象中存有建立链接以来所有的聊天记录
//由broker调用发送消息方法，根据id选择存入对象数据
public class FriendWindow {
    //    发送消息需要的token
    public String token;
    //属性1：好友个人资料
    public Context context;
    //属性2：对话资料
    public Map<String, MessageDto> talkList = new HashMap<>();
    //未读消息个数
    public int unreaded;
    //  位置
    public int weizhi;

//是否打开了对话框
    public Boolean dakai;


    //用与好友列表的是否已读
    public JLabel weidulabel;
    //    窗体属性
    public JFrame frame;
//对话窗口

//    对话数量


    //对话框
    private static HashMap<String, JFrame> sendMessage;

    //    方法1：构造方法，生成对象
    public FriendWindow(Context context, JFrame frame, int i, String token) {
        this.context = context;
        this.token = token;
        this.unreaded = 0;
        this.weizhi = i;
        this.dakai=false;
        this.weidulabel = new JLabel(unreaded + "条消息未读");
        this.frame = frame;

        touxianglan(frame, i);
        shifouweidu();
    }

//    方法3：生成好友信息  具备点击事件创造对话框功能


    //生成对话框
    public void touxianglan(JFrame frame, int i) {
        JLabel label4 = new JLabel("用户名:  " + context.getNickname());
        label4.setBounds(20, 120 + 100 * i, 200, 30);
        frame.add(label4);
        String id = context.getId();
        JLabel label5 = new JLabel("id:  " + id);
        label5.setBounds(150, 120 + 100 * i, 200, 30);
        frame.add(label5);

        JButton button = new JButton("发送消息");
        button.setBounds(250, 130 + 100 * i, 100, 30);     // x, y, width, height
        frame.add(button);


        button.addActionListener(event -> {   // 点击事件建对话框
            unreaded = 0;
            weidulabel.setText(unreaded + "条消息未读");
            shifouweidu();
            frame.repaint();
            try {
                makechat();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
    }
//单例对话框
    public static JFrame getInstance(String id) throws MalformedURLException {
        if (sendMessage == null) {
            sendMessage = new HashMap<String, JFrame>();
        }
        if (sendMessage.get(id) == null) {
            sendMessage.put(id, new JFrame());
        }
        return sendMessage.get(id);
    }


    //生成与喵喵喵的对话
    public void makechat() throws MalformedURLException {

        this.dakai=true;
//
        JFrame jFrame = getInstance(context.getId());

        jFrame.repaint();
//    加载历史聊天记录
        showhismessage(jFrame);

        jFrame.setTitle("与" + context.getNickname() + "的对话");

        jFrame.setSize(420, 620);
        jFrame.setLayout(null);
//lixiang1833
        JTextField field2 = new JTextField(20);
        field2.setBounds(10, 400, 365, 170);
        jFrame.add(field2);

        JButton button = new JButton("发送消息");
        button.setBounds(280, 340, 90, 40);     // x, y, width, height
        jFrame.add(button);

        button.addActionListener(event -> {   // 点击事件
            if (field2.getText().isEmpty()) {
                int res = JOptionPane.showConfirmDialog(jFrame, "不能发送空消息", "错误提示", 0);
            } else {
                try {
                    setMessage(field2.getText(), token, context.getId(), jFrame);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {

                System.out.println(" 窗口关闭");
                sendMessage.remove(context.getId());

            }
        });
        // 将按扭添加到窗体中
        jFrame.setVisible(true);
        this.dakai=false;
    }


    //发送消息功能
    @SneakyThrows
    public void setMessage(String message, String token, String id, JFrame jFrame) throws MalformedURLException {
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
        String data = "content=" + message + "&receiver_id=" + id + "&type=1&receiver_type=1";
        outputStream.write(data.getBytes());
        // 服务端返回的HTTP状态码
//
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
            SendMessageDto contextDto = mapper.readValue(row, SendMessageDto.class);
            setmap(contextDto.getData().get(0).getId(), contextDto.getData().get(0));

            showhismessage(jFrame);
        }
        // 关闭相关资源
        outputStream.close();
        reader.close();
        inputStream.close();
        conn.disconnect();
    }

    //    方法4； 根据信息显示在对话框
    public void notifyA(MessageDto message) {

        unreaded += 1;
        weidulabel.setText(unreaded + "条消息未读");
        shifouweidu();
        setmap(message.getId(), message);
        try {
                JFrame jFrame = getInstance(context.getId());
                jFrame.repaint();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
    }

    //  检测是否增加未读窗体
    public void shifouweidu() {
        weidulabel.setBounds(20, 140 + 100 * weizhi, 200, 30);
        frame.add(weidulabel);
        frame.repaint();
    }

    //  存入信息map
    public void setmap(String sendBy, MessageDto message) {
        talkList.put(sendBy, message);
    }

    //  读取并显示消息（在窗体中增加标签）
    public void showhismessage(JFrame jFrame) {
        Set<String> topic = talkList.keySet();
        int i = 0;
        for (String Item : topic) {
            JLabel label7 = new JLabel(talkList.get(Item).getSender_id().equals(context.getId()) ? context.getNickname() + "说" : "我说");
            label7.setBounds(20, 20 + 50 * i, 200, 30);
            jFrame.add(label7);

            JLabel  label6 = new JLabel(talkList.get(Item).getContent().getText() + "");
            label6.setBounds(20, 40 + 50 * i, 200, 30);
            jFrame.add(label6);
            i++;
        }
    }

}
