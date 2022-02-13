package com.example.demo.controller;

import com.example.demo.dto.TokenCreateDto;
import com.example.demo.service.TokenService;
import com.example.demo.util.JsonResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;
@CrossOrigin
@RestController
public class TokenController {
    @Resource
    TokenService tokenService;
    //用户登录
    @PostMapping("api/token/create")
    public JsonResult<Map<String,String>> create(@Valid TokenCreateDto tokenCreateDto){
        Map<String,String>data=  tokenService.create(tokenCreateDto);
        return  new JsonResult<>("获取token成功",data);
    }
    //删除token
    @GetMapping("api/token/delete")
    public JsonResult<Map<String,String>> delete(String token){

        System.out.println(tokenService.delete(token));
        return  new JsonResult<>("删除token成功");

    }
}
