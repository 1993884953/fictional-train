package com.example.gui;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Hello {
    //设置主窗口的宽高

    public static void main(String[] args) {
        int frameWidth=400;
        int frameHeight=800;
        JFrame frame = new JFrame();
        frame.setTitle("Hello");
        frame.setSize(frameWidth, frameHeight);

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
        int buttonWitch=200;
        int buttonHeight=50;
        button.setBounds((frameWidth-buttonWitch)/2, frameHeight-frameWidth/2, buttonWitch, buttonHeight);     // x, y, width, height

        frame.add(button);                    // 将按扭添加到窗体中


        int fieldWidth=frameWidth*3/5;
        int fieldHeight=fieldWidth/5;

        int labelWidth=frameWidth/5;
        int labelX=25;
        int labelY=60;
        int space=100;

        //账号输入框
        JLabel label1 = new JLabel("用户名:");
        label1.setBounds(labelX, labelY, labelWidth, fieldHeight);
        frame.add(label1);


        JTextField field1 = new JTextField(20);
        field1.setBounds(labelX+labelY, labelY, fieldWidth, fieldHeight);
        frame.add(field1);
        String info1 = "";
        field1.setText(info1);
        //密码输入框
        JLabel label2 = new JLabel("密码:");
        label2.setBounds(labelX, labelY+space, labelWidth, fieldHeight);
        frame.add(label2);


        JTextField field2 = new JTextField(20);
        field2.setBounds(labelX+labelY, labelY+space, fieldWidth, fieldHeight);
        frame.add(field2);
        String info2 = "";
        field2.setText(info2);
/////////////////////////////////////////////////////
        button.addActionListener(event -> {   // 点击事件
            System.out.println("被点了");
            if(field1.getText().length()==0||field2.getText().length()==0){
                int res = JOptionPane.showConfirmDialog(frame, "未输入用户名或密码", "登录提示", 0);
            }else {
                int res = JOptionPane.showConfirmDialog(frame, "登陆成功", "登录提示", 0);
            }
        });

        frame.setVisible(true);               // 放在最后面，组件都添加完成之后，才显示主窗体
    }
}