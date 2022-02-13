package com.example.myJFrame.util;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;



@Data
public class MyJTextField extends JTextField {

    private int x;
    private int y;
    private int width = 100;
    private int height = 50;

    public MyJTextField(String text, int columns, int x, int y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setText(text);
        setColumns(columns);
        setBounds(x, y, width, height);

    }

    public MyJTextField(int columns, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setColumns(columns);
        setBounds(x, y, width, height);
    }
}
