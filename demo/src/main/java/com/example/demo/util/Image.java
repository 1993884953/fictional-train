package com.example.demo.util;

import java.util.Random;

//图片链接生成类
public class Image {
    private static final String[] str = new String[]{
            "https://source.unsplash.com/random",
            "https://www.dmoe.cc/random.php",
            "https://img.paulzzh.tech/touhou/random",
            "https://acg.toubiec.cn/random.php",
            "https://www.52yih.com/apis/random/api.php",
            "https://api.nmb.show/xiaojiejie1.php",
            "https://api.btstu.cn/sjbz/?lx=dongman",
            "https://img.paulzzh.tech/touhou/random",
            "https://acg.toubiec.cn/random.php",
    };
    public static String getAvatar(){
        int verificationCode = new Random().nextInt(str.length-1);
//        System.out.println(str[verificationCode] + "+" + verificationCode);
        return str[verificationCode];
    }

}
