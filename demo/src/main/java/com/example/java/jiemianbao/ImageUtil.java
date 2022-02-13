package com.example.java.jiemianbao;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Patrick Ji
 * @description 图像工具类
 */
public class ImageUtil {
    /**
     * 缩放图像
     *
     * @param oldImageIcon 旧图像
     * @param width        宽度
     * @param height       高度
     * @return 新图像
     */
    private static ImageIcon resizeImageIcon(ImageIcon oldImageIcon, int width, int height) {
        Image img = oldImageIcon.getImage();
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(newImg);
    }

    /**
     * 根据头像 URL，将头像渲染到面板上，同时完成头像大小缩放的工作
     *
     * @param avatarUrlStr 头像 URL
     * @param panel        面板
     * @param x            x 坐标
     * @param y            y 坐标
     * @param width        宽
     * @param height       高
     */
    public static void renderAvatarUrlStrToPanel(String avatarUrlStr, JPanel panel, int x, int y, int width, int height) {
        try {
            URL avatarUrl = new URL(avatarUrlStr);
            ImageIcon avatarImage = new ImageIcon(avatarUrl);
            ImageIcon newAvatarImage = resizeImageIcon(avatarImage,width,height);
            JLabel avatar = new JLabel(newAvatarImage);
            avatar.setBounds(x, y, width, height);
            panel.add(avatar);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
