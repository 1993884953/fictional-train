package com.example.token.service;

import com.example.token.biz.SmsBiz;
import com.example.token.dto.SmsCreateRequest;
import com.example.token.mapper.SmsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service//告诉框架
public class SmsService implements SmsBiz {

    @Resource//自动连接数据库
    SmsMapper smsMapper;
    //查询数据库，看是否已经注册
    public Map<String, String> smsCreate(SmsCreateRequest smsCreateRequest) {
        //UUID获取32位校验码
        String verificationKey = UUID.randomUUID().toString().replaceAll("-", "");
        String verificationCode = String.valueOf(new Random().nextInt(899999) + 100000);

        //上传数据库
        smsCreateRequest.setVerificationKey(verificationKey);
        smsCreateRequest.setVerificationCode(verificationCode);
        if (smsMapper.smsCreate(smsCreateRequest)!=1){
            throw new RuntimeException("手机号无效");
        }

        //处理返回用户
        Map<String, String> data = new HashMap<>();
        data.put("verificationKey", verificationKey);

        return data;
    }

//    //查询数据库，看是否已经注册
//    public Result<Map<String, String>> smsCreate(SmsCreateRequest smsCreateRequest) {
//        //创建结果集
//        Result<Map<String, String>> result;
//
//        //UUID获取32位校验码
//        String verificationKey = UUID.randomUUID().toString().replaceAll("-", "");
//        String verificationCode = String.valueOf(new Random().nextInt(899999) + 100000);
//
//        //上传数据库
//        smsCreateRequest.setVerificationKey(verificationKey);
//        smsCreateRequest.setVerificationCode(verificationCode);
//        System.out.println(smsCreateRequest.toString());
//        smsMapper.insertSms(smsCreateRequest);
//
//        System.out.println("上传成功");
//        //处理返回用户
//        Map<String, String> data = new HashMap<>();
//        data.put("verificationKey", verificationKey);
//        result = new Result<>("SUCCESS", "验证码获取成功", data);
//
//
////        if (smsCreateRequest.getMobile() != null) {
////            Map<String, String> data = new HashMap<>();
////            //UUID获取32位校验码
////            String verificationKey =  UUID.randomUUID().toString().replaceAll("-", "");
////            data.put("verificationKey", verificationKey);
////            result = new Result<>("SUCCESS", "验证码获取成功", data);
////        } else {

////        }
//        return result;
//    }

}