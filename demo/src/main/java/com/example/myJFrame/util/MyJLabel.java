package com.example.myJFrame.util;

import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
@Data
public class MyJLabel extends JLabel {

    private int x=300;
    private int y=200;
    private int width=100;
    private int height=30;
    private String text="text";
    private String fontName="微软雅黑";
    private int fontStyle=Font.PLAIN;
    private int fontSize=16;

    public MyJLabel(String text,String fontName,int fontStyle,int fontSize,int x,int y,int width,int height) {
        this.x=x;
        this.y=y;
        this.text=text;
        this.width=width;
        this.height=height;
        this.fontName=fontName;
        this.fontSize=fontSize;
        this.fontStyle=fontStyle;

        setDefaultLocale(Locale.forLanguageTag(text));
        setFont(new Font(fontName,fontStyle, fontSize));
        setBounds(x, y, width, height);
    }
    public MyJLabel(String text,int x,int y,int width,int height) {
        this.x=x;
        this.y=y;
        this.text=text;
        this.width=width;
        this.height=height;


        setDefaultLocale(Locale.forLanguageTag(text));
        setFont(new Font(fontName,fontStyle, fontSize));
        setBounds(x, y, width, height);
    }


    public MyJLabel(int i, int x, int y, int width, int height) {
        this.x=x;
        this.y=y;
        this.text= String.valueOf(i);
        this.width=width;
        this.height=height;


        setDefaultLocale(Locale.forLanguageTag(text));
        setFont(new Font(fontName,fontStyle, fontSize));
        setBounds(x, y, width, height);
    }
}
