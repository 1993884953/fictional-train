package com.example.myJFrame.util;


import lombok.Data;

import javax.swing.*;

@Data
public class MyButton extends JButton {
//    JButton button = new JButton("发送");
//    int buttonWitch = 200;
//    int buttonHeight = 50;
//        button.setBounds(buttonWitch * 2 + buttonHeight, labelY * 8, buttonWitch / 2, buttonHeight);
    private String text;

    private int x;
    private int y;
    private int width = 100;
    private int height = 50;
    public MyButton(String text,int x, int y,int width,int height) {
        this.text=text;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        setBounds(x, y, width , height);
    }

    public MyButton() {
    }
}
