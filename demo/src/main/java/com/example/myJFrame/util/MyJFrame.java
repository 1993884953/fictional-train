package com.example.myJFrame.util;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class MyJFrame extends JFrame {
    private  int x=400;                //主窗口的x坐标
    private  int y=200;                //主窗口的y坐标
    private int width=400;           //主窗口的宽
    private int height=800;          //主窗口的高

    private String title="Hello,world!";         //主窗口的标题



    public MyJFrame(String title,int x, int y, int width, int height) throws HeadlessException {
        this.x=x;
        this.y=y;
        this.title=title;
        this.width=width;
        this.height=height;
        setTitle(title);
        setBounds(x,y,width,height);
        setDefaultCloseOperation(MyJFrame.DISPOSE_ON_CLOSE);
        setLayout(null);                // 取消窗体的默认布局
    }

    public MyJFrame() throws HeadlessException {
    }


}
