package com.example.token.util;

import java.util.Random;

//图片链接生成类
public class Image {
    private static String[] str = new String[]{
            "https://unsplash.it/1600/900?random",
            "https://source.unsplash.com/random",
            "https://www.dmoe.cc/random.php",
            "https://acg.yanwz.cn/api.php",
            "https://img.paulzzh.tech/touhou/random",
            "https://acg.toubiec.cn/random.php",
            "https://www.52yih.com/apis/random/api.php",
            "https://api.nmb.show/xiaojiejie1.php",
            "https://cdn.seovx.com/d/",
            "https://api.nmb.show/1985acg.php",
            "https://tuapi.eees.cc/dongman.php",
            "https://api.btstu.cn/sjbz/?lx=dongman",
            "https://img.xjh.me/random_img.php",
            "https://acg.yanwz.cn/api.php",
            "https://img.paulzzh.tech/touhou/random",
            "https://acg.toubiec.cn/random.php",
    };
    public static String getAvatar(){
        int verificationCode = new Random().nextInt(str.length-1);
//        System.out.println(str[verificationCode] + "+" + verificationCode);
        return str[verificationCode];
    }

}
