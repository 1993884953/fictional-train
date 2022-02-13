package com.example.token.controller;

import com.example.token.service.UserService;
import com.example.token.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
@RestController
public class TokenController {
    @Resource
    UserService userService;
    //登录流程
    @PostMapping("/api/token/create")
    public Result<Map<String, String>>create(String mobile, String password) {
        //验证成功 生成一个随机的字符串，存到token表
        Map <String,String>data= userService.login(mobile,password);
        return Result.<Map<String, String>>builder().code("SUCCESS").message("获取token成功").data(data).build();
    }
}
