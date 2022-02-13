package com.example.myJFrame.controller;

import com.example.myJFrame.service.TokenService;

public class TokenController {
    public void getToken(){
        System.out.println(new TokenService().getToken());
    }
}
