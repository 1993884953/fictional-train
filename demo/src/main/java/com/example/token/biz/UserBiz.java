package com.example.token.biz;

import com.example.token.dto.UserCreateRequest;

import java.util.Map;

//要实现的接口
public interface UserBiz {
    Map<String, String> userCreate(UserCreateRequest userCreateRequest);
    Map<String,String> login(String mobile,String password);

}
