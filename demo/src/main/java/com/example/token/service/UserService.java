package com.example.token.service;

import com.example.token.biz.UserBiz;
import com.example.token.dto.SmsCreateRequest;
import com.example.token.dto.UserCreateRequest;
import com.example.token.mapper.SmsMapper;
import com.example.token.mapper.TokenMapper;
import com.example.token.mapper.UserMapper;
import com.example.token.util.Image;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

@Service
public class UserService implements UserBiz {
    @Resource//自动连接数据库
    SmsMapper smsMapper;
    @Resource//自动连接数据库
    UserMapper userMapper;
    @Resource//自动连接数据库
    TokenMapper tokenMapper;
    //创建用户信息进行逻辑判断
    public Map<String,String> userCreate(UserCreateRequest userCreateRequest) {
        SmsCreateRequest smsCreateRequest=smsMapper.smsFind(userCreateRequest.getVerificationKey());
        //判断密钥是否和服务器一致
        if(smsCreateRequest==null){
            throw new RuntimeException("无此验证密钥");
        }
        //判断验证码是否和服务器一致
        if (!Objects.equals(smsCreateRequest.getVerificationCode(), userCreateRequest.getVerificationCode())){
            throw new RuntimeException("验证码输入错误");
        }
        //手机号与手机号不匹配
        if (!Objects.equals(smsCreateRequest.getMobile(), userCreateRequest.getMobile())){
            throw new RuntimeException("手机号与验证码不匹配");
        }
        //超过10分钟，返回空
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date oldTime = sdf.parse(smsCreateRequest.getCreatedAt());
            long min=(new Date().getTime()-oldTime.getTime())/1000/60;
            if (min>300){
            return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //查找数据库是否存在该手机号
        if(userMapper.userIdFind(userCreateRequest.getMobile())!=null){
            throw new RuntimeException("手机号已经存在");
        }

        //像对象插入图片，上传数据库
        userCreateRequest.setAvatar(Image.getAvatar());
        if (userMapper.userCreate(userCreateRequest)!=1){
            throw new RuntimeException("user数据库连接失败");
        }

        //token表创建手机号获取userId
        String token=UUID.randomUUID().toString().replaceAll("-", "");
        if(tokenMapper.tokenCreate(userCreateRequest.getId(),token)!=1){
            throw new RuntimeException("token数据库连接失败");
        }
        //随机生成token
        Map <String,String>data=new HashMap<>();
        data.put("token", token);
        return data;
    }
    //通过token查找用户信息
    public Map<String,String> login(String mobile,String password){

        if(mobile==null|| mobile.equals("") ||password==null|| password.equals("")){
            throw new RuntimeException("账号和密码不能为空");
        }

        //token表创建手机号获取userId
        String userId= userMapper.login(mobile,password);
        System.out.println(userId);
        if(userId==null){
            throw new RuntimeException("账号或密码错误");
        }
        String token=UUID.randomUUID().toString().replaceAll("-", "");
        if(tokenMapper.tokenCreate(userId,token)!=1){
            throw new RuntimeException("token数据库连接失败");
        }
        Map<String,String>data=new HashMap<>();
        data.put("token",token);
        return data;
    }
}
