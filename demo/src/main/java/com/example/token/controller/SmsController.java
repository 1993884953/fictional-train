package com.example.token.controller;

import com.example.token.dto.SmsCreateRequest;
import com.example.token.service.SmsService;

import com.example.token.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;


@RestController
public class SmsController {
    @Resource//加载服务类
    SmsService smsService;
    @PostMapping("/api/sms")//验证手机号格式是否正确，正确返回32位字串
    public Result<Map<String, String>> smsCreate(@Valid SmsCreateRequest smsCreateRequest) {
        Map<String, String> data = smsService.smsCreate(smsCreateRequest);
        return Result.<Map<String, String>>builder().code("SUCCESS").message("新增成功").data(data).build();
    }
}