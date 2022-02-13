package com.example.token.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

//文件上传
@RestController
public class UploadController {
    //配置文件注入
    @Value("${upload.path}")
    private  String uploadPath;
    @Value("${server.port}")
    private  String host;
    @Value("${spring.mvc.static-path-pattern}")
    private String uploads;
    @PostMapping("/upload/image")//传入数据，设置请求参数
    //请求时选中对应的类型，
    public  String image(@RequestParam("file") MultipartFile file) throws IOException {
        //上传检测
        if(file.isEmpty()){
            throw new RuntimeException("文件未上传");
        }
        //获取文件后缀
        int index= Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        String extname=file.getOriginalFilename().substring(index+1).toLowerCase();
        //判断上传格式
        String allowImgFormat="png,jpg,jpeg,gif";
        if(!allowImgFormat.contains(extname)){
            throw new RuntimeException("文件类型不被允许");
        }

        //拼接文件夹
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd/");
        String subPath=simpleDateFormat.format(new Date());
        //拼接文件名
        String saveName=subPath+subPath.replaceAll("/","-")+ UUID.randomUUID().toString().replaceAll("-","")+"."+extname;
        //String uploadPath="uploads/";
        //创建文件夹与文件名
        File dir =new File(uploadPath+subPath);
        if(!dir.exists()){
            //文件夹创建状态
            if(!dir.mkdirs()){
                throw new RuntimeException("文件夹创建失败");
            }
        }
        //设置保存文件的路径
        File save=new File(uploadPath+saveName);
        //获取绝对地址
        System.out.println(save.getAbsoluteFile());
        file.transferTo(save.getAbsoluteFile());
        //创建http
        String http="http://localhost:"+host+uploads.replaceAll("[*]","")+saveName;
        System.out.println(http);
        return http;
    }
}