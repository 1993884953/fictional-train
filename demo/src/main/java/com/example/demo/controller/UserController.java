package com.example.demo.controller;

import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserGetUserDto;
import com.example.demo.dto.UserGetVisitorDto;
import com.example.demo.dto.UserUpdateDto;
import com.example.demo.service.UserService;
import com.example.demo.util.JsonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

//BeanUtils
@RestController
@CrossOrigin
public class UserController {
    @Resource
    UserService userService;//加载服务类

    //用户注册
    @PostMapping("/api/user/create")
    public JsonResult<Map<String, String>> create(@Valid UserCreateDto userCreateDto) {
        Map<String, String> data = userService.create(userCreateDto);
        return new JsonResult<>("用户注册成功", data);
    }

    //验证密码
    @GetMapping("/api/user/pwd")
    public JsonResult<Map<String, String>> verifyPwd(@Valid String password) {
        if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{6,20}$", password)) {
            throw new RuntimeException("密码格式不正确");
        }
        return new JsonResult<>("密码格式正确");
    }

    //跟新手机号，密码，昵称，
    @PostMapping("/api/user/update")
    public JsonResult<Map<String, String>> update(@Valid UserUpdateDto userUpdateDto) {
        userService.update(userUpdateDto);
//        System.out.println(userUpdateDto);
        return new JsonResult<>("更新用户信息成功");
    }

    //通过token获取个人信息
    @GetMapping("/api/user/whoami")
    public JsonResult<UserGetUserDto> getUser(@Valid String token) {
        return new JsonResult<>("获取用户信息成功", userService.getUser(token));
    }

    @GetMapping("/api/user/visitor")
    public JsonResult<List<UserGetVisitorDto>> getVisitor(@Valid String token) {
        return new JsonResult<>("获取用户信息成功", userService.getVisitor());
    }

    //配置文件注入
    @PostMapping("/api/upload/avatar")//传入数据，设置请求参数
    public JsonResult<Map<String, String>> avatar(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, String> data = userService.avatar(file);
        return new JsonResult<>("头像上传成功", data);

    }

}
