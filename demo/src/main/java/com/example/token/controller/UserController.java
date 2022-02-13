package com.example.token.controller;

import com.example.token.dto.UserCreateRequest;
import com.example.token.entity.User;
import com.example.token.interceptor.UserContext;
import com.example.token.service.UserService;
import com.example.token.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Resource//加载服务类
    UserService  userService;
    //监听路由
    @PostMapping("/api/user/create")
    //将用户传入数据绑定到对象里面，request绑定验证器 ,BindingResult类,绑定错误消息(添加后全局绑定失效)
    public Result<Map<String, String>> userCreate(@Valid UserCreateRequest userCreateRequest) {
        Map<String, String> data =userService.userCreate(userCreateRequest);
        if (data==null){
            return Result.<Map<String, String>>builder().code("INVALID_VERIFICATION_CODE").message("短信验证码⽆效").data(null).build();
        }
        return Result.<Map<String, String>>builder().code("SUCCESS").message("注册成功").data(data).build();
    }
    @GetMapping("/api/user/whoami")
    public Result<Map<String, User>> findToken(String token) {
        //查token  得到一个userId
        Map<String,User> data=new HashMap<>();
        data.put("data",UserContext.getUser());
        return Result.<Map<String, User>>builder().code("SUCCESS").message("个人信息获取成功").data(data).build();
    }
//    //监听路由
//    @PostMapping("/api/user/create")
//    //将用户传入数据绑定到对象里面，request绑定验证器 ,BindingResult类,绑定错误消息(添加后全局绑定失效)
//    public String create(@Valid UserCreateRequest userCreateRequest) {
////        if(bindingResult.hasErrors()){
////            //输出错误消息
////            return bindingResult.getFieldError().getDefaultMessage();
////        }
//        System.out.println(userCreateRequest);
//
//        return userCreateRequest.toString();
//    }
}
